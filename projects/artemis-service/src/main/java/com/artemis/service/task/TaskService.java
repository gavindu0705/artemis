package com.artemis.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.artemis.core.Resolver;
import com.artemis.core.bean.Link;
import com.artemis.core.log.ALogger;
import com.artemis.core.result.LinksResult;
import com.artemis.core.result.MutiResult;
import com.artemis.core.result.Res;
import com.artemis.core.result.Result;
import com.artemis.core.result.SingleResult;
import com.artemis.core.util.DataUtil;
import com.artemis.core.util.PatternUtil;
import com.artemis.mongo.dao.JobDao;
import com.artemis.mongo.dao.MetadataDao;
import com.artemis.mongo.dao.PageDao;
import com.artemis.mongo.dao.TaskDao;
import com.artemis.mongo.po.FileData;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Metadata;
import com.artemis.mongo.po.Page;
import com.artemis.mongo.po.Pends;
import com.artemis.mongo.po.Task;
import com.artemis.mongo.po.Urls;
import com.artemis.service.FileDataService;
import com.artemis.service.JobStatService;
import com.artemis.service.UrlsService;
import com.artemis.service.util.Footprint;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TaskService {
	private JobDao jobDao = JobDao.getInstance();
	private PageDao pageDao = PageDao.getInstance();
	private TaskDao taskDao = TaskDao.getInstance();
	private MetadataDao metadataDao = MetadataDao.getInstance();
	private UrlsService urlsService = UrlsService.getInstance();
	private FileDataService fileDataService = FileDataService.getInstance();

	private static TaskService instance = new TaskService();

	private static final AtomicLong SUC_INC = new AtomicLong(0);
	private static final AtomicLong ERR_INC = new AtomicLong(0);

	public static final ALogger LOG = new ALogger(TaskService.class);

	private TaskService() {

	}

	public static TaskService getInstance() {
		return instance;
	}

	public ProductData process(Pends pends) {
		if (!isPendsAvaliable(pends)) {
			return null;
		}

		String url = pends.getId();
		String jobId = pends.getJobId();
		String sessionId = pends.getSessionId();

		FileData fileData = getFileData(url);

		if (fileData == null) {
			this.urlsService.pendsToErrsTaskNOFILE(pends);
			ERR_INC.incrementAndGet();
			return null;
		}

		try {
			String charset = pends.getCharset();
			if (StringUtils.isBlank(charset)) {
				charset = DataUtil.matchCharset(new String(fileData.getContent()));
			}
			
			String content = null;
			if (StringUtils.isBlank(charset)) {
				content = new String(fileData.getContent());
			} else {
				content = new String(fileData.getContent(), charset);
			}

			Job job = this.jobDao.findById(jobId);
			if (job == null) {
				this.urlsService.pendsToErrsTaskNOJOB(pends);
				return null;
			}

			// 匹配到的页面
			Page page = this.matchPage(jobId, url);
			if (page == null) {
				this.urlsService.pendsToErrsTaskNOPAGE(pends);
				return null;
			}

			List<Link> links = new ArrayList<Link>();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<Task> tasks = this.findTasks(job.getId(), page.getId());
			if (tasks.size() > 0) {
				for (Task task : tasks) {
					Map<String, Object> resultMap = this.taskExcutor(url, task, content, pends.getParams());
					if (resultMap.containsKey(Resolver.LINKS) && resultMap.get(Resolver.LINKS) instanceof LinksResult) {
						LinksResult linksResult = (LinksResult) resultMap.get(Resolver.LINKS);
						links.addAll(linksResult.getValue());
					} else {
						dataMap.putAll(resultMap);
					}
				}
			} else {
				this.urlsService.pendsToErrsTaskNOTASK(pends);
				return null;
			}

			ProductData ret = new ProductData();
			ret.setUrl(url);
			ret.setMatchPage(page);
			if (dataMap.size() > 0) {
				Metadata metadata = dataToMetadata(url, jobId, sessionId, job.getCat(), dataMap, pends.getParams());
				ret.setMetadata(metadata);
			}
			if (links.size() > 0) {
				List<Urls> urls = linkToUrls(links, jobId, sessionId, pends.getCharset(), pends.getParams());
				ret.setUrls(urls);
			}

			SUC_INC.incrementAndGet();
			this.urlsService.updatePendsTaskSuc(pends.getId());
			return ret;
		} catch (Exception e) {
			LOG.error("", e);
			ERR_INC.incrementAndGet();
			this.urlsService.pendsToErrsTaskErr(pends);
			return null;
		}
	}

	public int saveUrlsToCrawlInit(List<Urls> urls) {
		if (urls == null || urls.size() == 0) {
			return 0;
		}
		int ret = 0;
		for (Urls u : urls) {
			if (!Footprint.getInstance().exists(u.getId(), u.getJobId(), u.getSessionId())) {
				urlsService.addUrlsCrawlInit(u.getId(), u.getReferer(), u.getCharset(), u.getJobId(), u.getSessionId(),
						u.getParams());
				Footprint.getInstance().print(u.getId(), u.getJobId(), u.getSessionId());
				ret++;
			} else {
				// LOG.print("exists url " + u.getJobId() + "  " +
				// u.getSessionId() + "  " + u.getId());
			}
		}
		return ret;
	}

	public int saveMetadata(Metadata metadata) {
		if (metadata != null && metadata.getUrl() != null) {
			// 记录日志
			JobStatService.getInstance().increaseMetaCount(metadata.getJobId(), metadata.getSessionId());
			this.metadataDao.save(metadata);
			return 1;
		}
		return 0;
	}

	public long findMetadataCount() {
		return this.metadataDao.findCount();
	}

	public long findMetadataCount(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		return this.metadataDao.findCount(q);
	}

	private Map<String, Object> taskExcutor(String url, Task task, String content, Map<String, String> extraParams) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (extraParams != null) {
			params.putAll(extraParams);
		}
		params.put(Resolver.CONENT, content);
		params.put(Resolver.URL, url);
		if (task.getParams() != null) {
			params.putAll(task.getParams());
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			Result res = executeClazz(task.getClazz(), params);
			if (res instanceof SingleResult) {
				SingleResult result = (SingleResult) res;
				if (result instanceof LinksResult) {
					ret.put(Resolver.LINKS, result);
				} else {
					ret.put(result.getName(), result.getValue());
				}
			} else if (res instanceof MutiResult) {
				MutiResult result = (MutiResult) res;
				for (Res r : result.getResult()) {
					ret.put(r.getName(), r.getValue());
				}
			}
		} catch (Exception e) {
			LOG.error("url:" + url + "\tclazz:" + task.getClazz() + "\tselector:" + params.get(Resolver.SELECTOR), e);
		}

		return ret;
	}

	/**
	 * 匹配页面
	 * 
	 * @param jobId
	 * @param url
	 * @return
	 */
	public Page matchPage(String jobId, String url) {
		List<Page> pages = pageDao.findPageByJobId(jobId);
		for (Page page : pages) {
			for (String patt : page.getPatterns()) {
				Pattern pattern = PatternUtil.compile(patt);
				Matcher matcher = pattern.matcher(url);
				if (matcher.matches()) {
					return page;
				}
			}
		}
		return null;
	}

	private List<Task> findTasks(String jobId, String pageId) {
		return this.taskDao.findTaskByJobPageId(jobId, pageId);
	}

	private Metadata dataToMetadata(String url, String jobId, String sessionId, String cat, Map<String, Object> dataMap,
			Map<String, String> extraParams) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (extraParams != null) {
			resultMap.putAll(extraParams);
		}
		if (dataMap != null) {
			resultMap.putAll(dataMap);
		}
		Metadata metadata = new Metadata();
		metadata.setUrl(url);
		metadata.setJobId(jobId);
		metadata.setSessionId(sessionId);
		metadata.setCat(cat);
		metadata.setData(resultMap);
		metadata.setCreationDate(new Date());
		return metadata;
	}

	@SuppressWarnings("rawtypes")
	private Result executeClazz(String clazz, Map<String, Object> params) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class clazzObj = Class.forName(clazz);
		Resolver resolver = (Resolver) clazzObj.newInstance();
		return resolver.invoke(params);
	}

	private boolean isPendsAvaliable(Pends pends) {
		if (pends == null || pends.getId() == null) {
			return false;
		}

		String url = pends.getId();
		String jobId = pends.getJobId();
		if (url == null || jobId == null) {
			return false;
		}

		return true;
	}

	private FileData getFileData(String url) {
		FileData fileData = fileDataService.findByUrl(url);
		if (fileData == null || fileData.getContent() == null || fileData.getContent().length == 0) {
			return null;
		}

		return fileData;
	}

	private List<Urls> linkToUrls(List<Link> links, String jobId, String sessionId, String charset,
			Map<String, String> extraParams) {
		List<Urls> ret = new ArrayList<Urls>();
		for (Link link : links) {
			if (StringUtils.isBlank(link.getUrl())) {
				continue;
			}
			Urls entity = new Urls();
			entity.setId(link.getUrl());
			entity.setReferer(link.getReferer());
			entity.setJobId(jobId);
			entity.setSessionId(sessionId);
			entity.setCharset(charset);
			entity.setStatus(Urls.CRAWL_INIT);
			entity.setParams(extraParams);
			entity.setCreationDate(new Date());
			ret.add(entity);
		}
		return ret;
	}
}
