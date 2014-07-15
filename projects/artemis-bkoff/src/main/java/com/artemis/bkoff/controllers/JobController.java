package com.artemis.bkoff.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.sothis.core.util.Pager;

import com.artemis.bkoff.model.JobModel;
import com.artemis.bkoff.model.JsonModel;
import com.artemis.bkoff.vo.JobVo;
import com.artemis.bkoff.vo.PageVo;
import com.artemis.core.bean.Harvest;
import com.artemis.core.bean.HtmlGoal;
import com.artemis.core.util.BeanUtils;
import com.artemis.core.util.DataUtil;
import com.artemis.mongo.dao.JobDao;
import com.artemis.mongo.dao.JobStatDao;
import com.artemis.mongo.dao.PageDao;
import com.artemis.mongo.dao.SeedDao;
import com.artemis.mongo.dao.SiteConfigDao;
import com.artemis.mongo.dao.TaskDao;
import com.artemis.mongo.dao.UrlRoadDao;
import com.artemis.mongo.po.FileData;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.JobStat;
import com.artemis.mongo.po.Page;
import com.artemis.mongo.po.Pends;
import com.artemis.mongo.po.Seed;
import com.artemis.mongo.po.Seed.StatusEnum;
import com.artemis.mongo.po.SiteConfig;
import com.artemis.mongo.po.Task;
import com.artemis.mongo.po.UrlRoad;
import com.artemis.mongo.po.Urls;
import com.artemis.mongo.po.Urls.UrlsStatusEnum;
import com.artemis.service.FileDataService;
import com.artemis.service.JobService;
import com.artemis.service.SiteConfigService;
import com.artemis.service.UrlsService;
import com.artemis.service.proxy.NginxProxyImpl;
import com.artemis.service.task.ProductData;
import com.artemis.service.task.TaskService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JobController {
	private JobDao jobDao = JobDao.getInstance();
	private SeedDao seedDao = SeedDao.getInstance();
	private PageDao pageDao = PageDao.getInstance();
	private TaskDao taskDao = TaskDao.getInstance();
	private JobStatDao jobStatDao = JobStatDao.getInstance();
	private UrlRoadDao urlRoadDao = UrlRoadDao.getInstance();
	private SiteConfigDao siteConfigDao = SiteConfigDao.getInstance();

	private JobService jobService = JobService.getInstance();
	private TaskService taskService = TaskService.getInstance();
	private UrlsService urlsService = UrlsService.getInstance();
	private FileDataService fileDataService = FileDataService.getInstance();
	private SiteConfigService siteConfigService = SiteConfigService.getInstance();

	public Object crawlDataAction(JobModel model) throws UnsupportedEncodingException {
		FileData fileData = this.fileDataService.findByUrl(model.getUrl());
		List<Seed> seeds = this.seedDao.findAllSeedByJobId(model.getJobId(), StatusEnum.ENABLE);
		if (seeds.size() > 0) {
			String content = new String(fileData.getContent(), seeds.get(0).getCharset());
			model.setContent(content);
		}
		return model;
	}

	public Object urlRoadAction(JobModel model) {
		Job job = jobDao.findById(model.getJobId());
		model.setJobVo(convertJobToVo(job));

		if (StringUtils.isBlank(model.getRequestUrl()) && StringUtils.isBlank(model.getRefererUrl())) {
			List<Seed> seeds = this.seedDao.findAllSeedByJobId(job.getId(), StatusEnum.ENABLE);
			if (seeds.size() > 0) {
				model.setSeed(seeds.get(0));
			}
			return model;
		}

		DBObject q = new BasicDBObject();
		if (StringUtils.isNotBlank(model.getRequestUrl())) {
			q.put("request_url", model.getRequestUrl());
		} else if (StringUtils.isNotBlank(model.getRefererUrl())) {
			q.put("referer_url", model.getRefererUrl());
		}
		q.put("job_id", model.getJobId());
		q.put("session_id", model.getSessionId());

		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		List<UrlRoad> urlRoadList = this.urlRoadDao.findAll(q, sq);
		Map<String, List<UrlRoad>> urlRoadMap = new LinkedHashMap<String, List<UrlRoad>>();
		for (UrlRoad urlRoad : urlRoadList) {
			if (urlRoadMap.containsKey(urlRoad.getRequestUrl())) {
				List<UrlRoad> list = urlRoadMap.get(urlRoad.getRequestUrl());
				list.add(urlRoad);
				urlRoadMap.put(urlRoad.getRequestUrl(), list);
			} else {
				List<UrlRoad> list = new ArrayList<UrlRoad>();
				list.add(urlRoad);
				urlRoadMap.put(urlRoad.getRequestUrl(), list);
			}
		}
		model.setUrlRoadMap(urlRoadMap);
		return model;
	}

	public Object listAction(JobModel model) {
		Pager pager = model.getPager(50);

		DBObject q = new BasicDBObject();
		List<Job> jobList = null;

		if (StringUtils.isNotBlank(model.getCat())) {
			q.put("cat", model.getCat());
		} else {
			if (model.getStatus() != null) {
				if (model.getStatus() == 1) {
					q.put("status", 1);
				} else if (model.getStatus() == 0) {
					q.put("status", 0);
				}
			}
		}

		model.setCatList(jobService.getJobCats());
		
		model.setRunningCount((int) jobDao.findCount(new BasicDBObject("status", 1)));
		model.setStoppingCount((int) jobDao.findCount(new BasicDBObject("status", new BasicDBObject("$ne", 1))));
		model.setJobCount((int) jobDao.findCount());

		pager.setTotalRows((int) jobDao.findCount(q));
		jobList = jobDao.findAll(q, new BasicDBObject("sequence", -1), pager.getStartRow(), pager.getPageSize());
		List<JobVo> voList = new ArrayList<JobVo>();
		for (Job job : jobList) {
			JobVo vo = convertJobToVo(job);
			if (vo != null) {
				voList.add(vo);
			}
		}
		model.setJobVoList(voList);
		model.setUrlsCount(urlsService.findUrlsCrawlInitCount());
		model.setPendsCount(urlsService.findPendsTaskInitCount());
		model.setMetaCount(taskService.findMetadataCount());

		return model;
	}

	public Object editSequenceAction(JobModel model) {
		model.setViewType("json");

		if (model.getJobId() == null || !ObjectId.isValid(model.getJobId())) {
			return model;
		}

		Job job = jobDao.findById(model.getJobId());
		if (job == null) {
			return model;
		}

		if ("top".equals(model.getType())) {
			List<Job> jobList = jobDao.findAll(new BasicDBObject(), new BasicDBObject("sequence", -1), 0, 1);
			if (jobList == null || jobList.size() == 0) {
				return model;
			}
			Job uJob = jobList.get(0);
			jobDao.update(new BasicDBObject("sequence", uJob.getSequence() + 1),
					new BasicDBObject("_id", new ObjectId(job.getId())));
		} else {
			List<Job> jobList = jobDao.findAll(new BasicDBObject("sequence", new BasicDBObject("$gt", job.getSequence())),
					new BasicDBObject("sequence", 1), 0, 1);
			if (jobList == null || jobList.size() == 0) {
				return model;
			}
			Job uJob = jobList.get(0);
			jobDao.update(new BasicDBObject("sequence", uJob.getSequence()),
					new BasicDBObject("_id", new ObjectId(model.getJobId())));
			jobDao.update(new BasicDBObject("sequence", job.getSequence()), new BasicDBObject("_id", new ObjectId(uJob.getId())));
		}
		return model;
	}

	public Object statAction(JobModel model) {
		Job job = jobDao.findById(model.getJobId());
		model.setJobVo(convertJobToVo(job));

		model.setJobMetaCount(taskService.findMetadataCount(model.getJobId()));

		Integer status = model.getStatus();
		if (status != null) {
			UrlsStatusEnum statusEnum = UrlsStatusEnum.parseUrlsStatus(status.intValue());
			if (statusEnum.getStatus() > 5) {
				model.setPendsList(urlsService.findPendsByJobId(model.getJobId(), statusEnum, 20));
			} else {
				model.setUrlsList(urlsService.findUrlsByJobId(model.getJobId(), statusEnum, 20));
			}
		}

		Map<String, Long> urlDataCountMap = new LinkedHashMap<String, Long>();
		for (UrlsStatusEnum statusEnum : UrlsStatusEnum.values()) {

			if (statusEnum.getStatus() > 5) {
				urlDataCountMap.put(statusEnum.name(), urlsService.findPendsCount(job.getId(), statusEnum));
			} else {
				urlDataCountMap.put(statusEnum.name(), urlsService.findUrlsCount(job.getId(), statusEnum));
			}
		}

		model.setUrlDataCountMap(urlDataCountMap);

		return model;
	}

	private JobVo convertJobToVo(Job job) {
		if (job == null) {
			return null;
		}

		JobVo jobVo = new JobVo();
		BeanUtils.copyProperties(job, jobVo);
		JobStat jobStat = jobStatDao.findJobStat(job.getId(), job.getSessionId());
		if (jobStat != null) {
			jobVo.setSessionId(job.getSessionId());
			jobVo.setStartDate(jobStat.getStartDate());
			jobVo.setEndDate(jobStat.getEndDate());
			jobVo.setCrawlCount(jobStat.getCrawlCount());
			jobVo.setTaskCount(jobStat.getTaskCount());
			jobVo.setErrCount(jobStat.getErrCount());
			jobVo.setMetaCount(jobStat.getMetaCount());
			jobVo.setStartDate(jobStat.getStartDate());
			jobVo.setEndDate(jobStat.getEndDate());
		} else {
			jobVo.setStartDate(null);
			jobVo.setEndDate(null);
		}

		return jobVo;
	}

	public Object deleteAction(JobModel model) {
		JsonModel ret = new JsonModel();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("msg", 1);

		this.jobDao.deleteById(model.getJobId());
		this.pageDao.deleteByJobId(model.getJobId());
		this.taskDao.deleteByJobId(model.getJobId());
		this.seedDao.deleteByJobId(model.getJobId());

		ret.setJsonModel(data);
		return ret;
	}

	public Object createAction(JobModel model) {
		if (model.getJobId() != null) {
			model.setJob(this.jobDao.findById(model.getJobId()));
		}
		model.setSiteConfigList(this.siteConfigDao.findAll());
		return model;
	}

	public Object saveSeedAction(JobModel model) {
		if (StringUtils.isBlank(model.getJobId())) {
			return model.redirect("/job/list");
		}

		if (StringUtils.isBlank(model.getSeeds())) {
			return model.redirect("/job/jobSeed?jobId=" + model.getJobId());
		}

		List<String> seedList = parseToSingles(model.getSeeds());
		for (String s : seedList) {
			s = s.trim();
//			SiteConfig siteConfig = siteConfigService.parseUrl(s);
			Seed entity = new Seed();
//			if (siteConfig != null) {
//				entity.setCharset(siteConfig.getCharset());
//			}
			entity.setCreationDate(new Date());
			entity.setJobId(model.getJobId());
			entity.setParams(null);
			entity.setStatus(StatusEnum.ENABLE.getStatus());
			entity.setUrl(s);
			saveOrUpdateSeed(entity);
		}

		return model.redirect("/job/jobSeed?jobId=" + model.getJobId());
	}

	private List<String> parseToSingles(String seeds) {
		String[] seedArray = seeds.split("\\r\n");
		List<String> ret = new ArrayList<String>();
		for (String seed : seedArray) {
			if (!seed.startsWith("http://")) {
				continue;
			}

			if (seed.indexOf("${") > 0 && seed.indexOf("}") > 0) {
				String str = StringUtils.substringBetween(seed, "${", "}");
				if (str.indexOf("-") > -1) {
					String[] arrs = str.split("-");
					int min = Integer.parseInt(arrs[0]);
					int max = Integer.parseInt(arrs[1]);
					for (int i = min; i <= max; i++) {
						ret.add(seed.replace("${" + str + "}", String.valueOf(i)));
					}
				}
			} else {
				ret.add(seed);
			}
		}
		return ret;
	}

	private void saveOrUpdateSeed(Seed seed) {
		Seed obj = this.seedDao.findSeedByUrl(seed.getJobId(), seed.getUrl());
		seed.setCreationDate(new Date());
		if (obj != null) {
			this.seedDao.deleteById(obj.getId());
			this.seedDao.save(seed);
		} else {
			this.seedDao.save(seed);
		}
	}

	public Object editSeedAction(JobModel model) {
		model.setViewType("json");
		if (model.getSeedId() == null) {
			return model;
		}

		DBObject data = new BasicDBObject();
		if (StringUtils.isNotBlank(model.getNewSeed()) && model.getNewSeed().startsWith("http://")) {
			data.put("url", model.getNewSeed().trim());
		}

		if (StringUtils.isNotBlank(model.getNewParam())) {
			String newParam = DataUtil.toDBCcase(model.getNewParam());
			Map<String, String> paramsMap = DataUtil.parse(newParam, ",", ":");
			Map<String, Object> proMap = new HashMap<String, Object>();
			for (String key : paramsMap.keySet()) {
				if (StringUtils.isBlank(paramsMap.get(key))) {
					continue;
				}
				proMap.put(key, paramsMap.get(key).trim());
			}

			String params = DataUtil.mapToString(proMap, ",");
			if (StringUtils.isNotBlank(params)) {
				data.put("params", params);
			}
		}

		if (StringUtils.isNotBlank(model.getNewCharset())) {
			data.put("charset", model.getNewCharset().trim());
		}

		if (data.toMap().size() > 0) {
			seedDao.updateById(data, model.getSeedId());
		}
		return model;
	}

	public Object jobSeedAction(JobModel model) {
		String jobId = model.getJobId();
		model.setJobVo(convertJobToVo(jobDao.findById(jobId)));
		Pager pager = model.getPager(100);
		StatusEnum statusEnum = StatusEnum.parseUrlsStatus(model.getStatus());

		DBObject q = new BasicDBObject("job_id", jobId);
		if (model.getStatus() != null && statusEnum != null) {
			q.put("status", statusEnum.getStatus());
			if (StringUtils.isNotBlank(model.getKeyword())) {
				q.put("params", new BasicDBObject("$regex", ".*" + model.getKeyword() + ".*"));
			}
		} else {
			if (StringUtils.isNotBlank(model.getKeyword())) {
				q.put("params", new BasicDBObject("$regex", ".*" + model.getKeyword() + ".*"));
			}
		}

		pager.setTotalRows((int) seedDao.findCount(q));
		List<Seed> seeds = seedDao.findAll(q, new BasicDBObject("c_date", 1), pager.getStartRow(), pager.getPageSize());
		model.setSeedList(seeds);
		q.put("status", StatusEnum.ENABLE.getStatus());
		model.setEnableCount((int) seedDao.findCount(q));
		q.put("status", StatusEnum.DISABLE.getStatus());
		model.setDisableCount((int) seedDao.findCount(q));
		q.removeField("status");
		model.setTotalCount((int) seedDao.findCount(q));
		return model;
	}

	public Object disableSeedAction(JobModel model) {
		if (model.getJobId() == null) {
			return model.redirect("/job/list");
		}
		if ("all".equals(model.getType())) {
			this.seedDao.updateSeedStatus(model.getJobId(), StatusEnum.DISABLE);
		} else if (model.getUrls() != null) {
			for (String url : model.getUrls()) {
				this.seedDao.updateSeedStatus(model.getJobId(), url, StatusEnum.DISABLE);
			}
		}
		return model.redirect("/job/jobSeed?jobId=" + model.getJobId());
	}

	public Object enableSeedAction(JobModel model) {
		if (model.getJobId() == null) {
			return model.redirect("/job/list");
		}

		if ("all".equals(model.getType())) {
			this.seedDao.updateSeedStatus(model.getJobId(), StatusEnum.ENABLE);
		} else if (model.getUrls() != null) {
			for (String url : model.getUrls()) {
				this.seedDao.updateSeedStatus(model.getJobId(), url, StatusEnum.ENABLE);
			}
		}
		return model.redirect("/job/jobSeed?jobId=" + model.getJobId());
	}

	public Object copyJobAction(JobModel model) {
		Job job = this.jobDao.findById(model.getJobId());
		if (job != null) {
			String oldJobId = job.getId();
			job.setId(null);
			job.setName("【请改名】" + job.getName() + "【请改名】");
			job.setStartDate(null);
			job.setSequence(job.getSequence() - 100);
			job.setStatus(Job.STOPPING);
			job.setCreationDate(new Date());
			job.setSessionId(ObjectId.get().toString());
			this.jobDao.save(job);
			String newJobId = job.getId();

			List<Page> pages = this.pageDao.findPageByJobId(oldJobId);
			for (Page page : pages) {
				String oldPageId = page.getId();
				List<Task> taskList = this.taskDao.findTaskByJobPageId(oldJobId, oldPageId);
				page.setId(null);
				page.setJobId(newJobId);
				page.setCreationDate(new Date());
				this.pageDao.save(page);
				String newPageId = page.getId();
				for (Task task : taskList) {
					task.setId(null);
					task.setJobId(newJobId);
					task.setPageId(newPageId);
					task.setCreationDate(new Date());
					Map<String, String> params = task.getParams();
					Map<String, String> newParams = new HashMap<String, String>();
					for (String key : params.keySet()) {
						if (key.startsWith("$")) {
							newParams.put(key.toString(), params.get(key));
						} else {
							newParams.put(key, params.get(key));
						}
					}
					task.setParams(newParams);
					this.taskDao.save(task);
				}
			}

		}
		return model.redirect("/job/list");
	}

	public Object saveAction(JobModel model) {
		if (model.getJob() == null) {
			return model.redirect("/job/list");
		}
		if (StringUtils.isBlank(model.getJob().getName()) || StringUtils.isBlank(model.getJob().getCat())
				|| StringUtils.isBlank(model.getJob().getRoot())) {
			return model.redirect("/job/list");
		}

		if (model.getJob().getId() != null) {
			DBObject data = new BasicDBObject();
			data.put("cat", model.getJob().getCat().trim());
			data.put("name", model.getJob().getName().trim());
			data.put("priority", model.getJob().getPriority());
			data.put("interval", model.getJob().getInterval());
			data.put("worktime", model.getJob().getWorktime());
			data.put("root", model.getJob().getRoot());
			data.put("sequence", model.getJob().getSequence());
			this.jobDao.updateById(data, model.getJob().getId());
		} else {
			Job job = model.getJob();
			job.setStatus(0);
			job.setCreationDate(new Date());
			job.setSessionId(ObjectId.get().toString());
			this.jobDao.save(job);
		}

		return model.redirect("/job/list");
	}

	public Object startAction(JobModel model) {
		if (model.getJobId() == null) {
			return model.redirect("/job/list");
		}
		jobService.startJob(model.getJobId());
		return model.redirect("/job/stat?jobId=" + model.getJobId());
	}

	public Object stopAction(JobModel model) {
		if (model.getJobId() == null) {
			return model.redirect("/job/list");
		}
		jobService.stopJob(model.getJobId());
		return model.redirect("/job/stat?jobId=" + model.getJobId());
	}

	public Object cleanAction(JobModel model) {
		JsonModel ret = new JsonModel();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("msg", 1);
		if (model.getJobId() == null) {
			data.put("msg", 0);
			return model;
		}
		jobService.cleanMetadata(model.getJobId());
		ret.setJsonModel(data);
		return ret;
	}

	public Object listPageAction(JobModel model) {
		Job job = jobDao.findById(model.getJobId());
		List<Page> pages = pageDao.findPageByJobId(model.getJobId());
		model.setJob(job);
		List<PageVo> pageVoList = new ArrayList<PageVo>();

		for (Page page : pages) {
			List<Task> tasks = this.taskDao.findTaskByJobPageId(model.getJobId(), page.getId());
			PageVo pageVo = new PageVo();
			BeanUtils.copyProperties(page, pageVo);
			pageVo.setTasks(tasks);
			pageVoList.add(pageVo);
		}
		model.setPageVoList(pageVoList);
		return model;
	}

	// public Object editPageAction(JobModel model) {
	// Job job = jobDao.findById(model.getJobId());
	// Page page = pageDao.findById(model.getPageId());
	// model.setJob(job);
	// model.setPage(page);
	// return model;
	// }

	public Object createPageAction(JobModel model) {
		model.setJob(this.jobDao.findById(model.getJobId()));
		if (model.getPageId() != null) {
			model.setPage(this.pageDao.findById(model.getPageId()));
		}
		return model;
	}

	public Object deletePageAction(JobModel model) {
		this.pageDao.deleteById(model.getPageId());
		this.taskDao.deleteByPageId(model.getPageId());
		return model.redirect("/job/listPage?jobId=" + model.getJobId());
	}

	public Object deleteSeedAction(JobModel model) {
		this.seedDao.deleteById(model.getSeedId());
		return model.redirect("/job/jobSeed?jobId=" + model.getJobId());
	}

	public Object savePageAction(JobModel model) {
		if (model.getPage() == null || StringUtils.isBlank(model.getPage().getName()) || StringUtils.isBlank(model.getPatterns())) {
			return model.redirect("/job/listPage?jobId=" + model.getJobId());
		}
		if (model.getPage().getId() != null) {
			DBObject data = new BasicDBObject();
			data.put("job_id", model.getPage().getJobId());
			data.put("name", model.getPage().getName());
			data.put("expires", model.getPage().getExpires());
			data.put("patterns", split(model.getPatterns()));
			data.put("c_date", new Date());
			this.pageDao.updateById(data, model.getPage().getId());
		} else {
			Page page = model.getPage();
			page.setPatterns(split(model.getPatterns()));
			page.setCreationDate(new Date());
			this.pageDao.save(page);
		}

		return model.redirect("/job/listPage?jobId=" + model.getJobId());
	}

	private List<String> split(String s) {
		String[] pts = s.split("\r\n");
		List<String> ret = new ArrayList<String>();
		for (int i = 0; i < pts.length; i++) {
			if (StringUtils.isNotBlank(pts[i])) {
				ret.add(pts[i]);
			}
		}
		return ret;
	}

	public Object testUrlAction(JobModel model) throws IOException {
		if (model.getJobId() == null) {
			return model.redirect("/job/list");
		}

		model.setJob(this.jobDao.findById(model.getJobId()));
		if (model.getUrl() != null && model.getCharset() != null) {
			model.setUrl(model.getUrl().trim());
			String referer = "http://www.baidu.com";// +
													// StringUtils.substringBetween(model.getUrl(),
													// "http://", "/");
			if (testAndSaveCrawlData(model.getUrl(), model.getCharset(), referer)) {
				ProductData productData = testProductData(model.getUrl(), model.getJobId(), model.getCharset(),
						model.getReferer(), DataUtil.stringToMap(model.getExtraParams(), ",", ":"));
				model.setProductData(productData);
			} else {
				model.setMsg("抓取失败");
			}
		} else {
			model.setMsg("请输入测试URL");
		}

		return model;
	}

	private ProductData testProductData(String url, String jobId, String charset, String referer, Map<String, String> extraParams) {
		Pends pends = new Pends();
		pends.setId(url);

		pends.setCharset(charset);
		pends.setCreationDate(new Date());
		pends.setErrors(0);
		pends.setJobId(jobId);
		pends.setParams(extraParams);
		pends.setReferer(referer);
		pends.setStatus(Urls.TASK_INIT);

		return this.taskService.process(pends);
	}

	private boolean testAndSaveCrawlData(String url, String charset, String referer) {
		HtmlGoal goal = new HtmlGoal(url, charset, referer);
		NginxProxyImpl adslProxy = new NginxProxyImpl();
		Harvest harvest = adslProxy.apply(goal);
		if (harvest.getStatusCode() != 200) {
			return false;
		}

		FileData fileData = new FileData();
		fileData.setId(harvest.getUrl());
		fileData.setReferer(harvest.getReferer());
		fileData.setMimeType(harvest.getMimeType());
		fileData.setContent(harvest.getContent());
		fileData.setCreationDate(new Date());
		fileDataService.saveFileData(fileData);
		return true;
	}
}
