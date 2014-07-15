package com.artemis.bkoff.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.artemis.bkoff.vo.JobVo;
import com.artemis.bkoff.vo.PageVo;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Page;
import com.artemis.mongo.po.Pends;
import com.artemis.mongo.po.Seed;
import com.artemis.mongo.po.SiteConfig;
import com.artemis.mongo.po.Task;
import com.artemis.mongo.po.UrlRoad;
import com.artemis.mongo.po.Urls;
import com.artemis.mongo.po.Urls.UrlsStatusEnum;
import com.artemis.service.task.ProductData;

public class JobModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4405543478557926498L;

	private String url;
	private String charset;
	private String referer;

	private Page page;
	private Job job;
	private Task task;
	private Seed seed;
	private Pends pends;

	private String jobId;
	private String pageId;
	private String taskId;
	private String seedId;

	private String sessionId;

	private String requestUrl;
	private String refererUrl;

	private String source;
	private String target;

	private List<Urls> urlsList;
	private List<Pends> pendsList;
	private List<UrlRoad> urlRoadList;
	private Map<String, List<UrlRoad>> urlRoadMap;

	private List<Job> jobList;
	private List<Page> pageList;
	private List<Task> taskList;
	private List<Seed> seedList;

	private JobVo jobVo;
	private PageVo pageVo;
	private List<JobVo> jobVoList;
	private List<PageVo> pageVoList;

	private List<SiteConfig> siteConfigList;

	private String cat;
	private String content;
	private String seeds;
	private String patterns;
	private String stopPatterns;
	private Integer status;

	private String resolverName;
	private String resolverClazz;
	private Map<String, String> resolverMap;

	private String extraParams;

	private ProductData productData;
	private String msg;

	private long urlsCount;
	private long pendsCount;
	private long metaCount;

	private long jobMetaCount;

	private Map<String, Long> urlDataCountMap;

	private long crawlInitCount;
	private long crawlQueCount;
	private long crawlSucCount;
	private long crawlErrCount;
	private long crawl400Count;
	private long crawl500Count;

	private long taskInitCount;
	private long taskQueCount;
	private long taskSucCount;
	private long taskErrCount;
	private long taskNoJobCount;
	private long taskNoPageCount;
	private long taskNoFileCount;
	private long taskNoTaskCount;

	private int runningCount;
	private int stoppingCount;
	private int jobCount;
	private List<String> urls;
	private String type;
	private int totalCount;
	private int disableCount;
	private int enableCount;
	private String keyword;

	private String newSeed;
	private String newParam;
	private String newCharset;

	private List<String> catList;

	public long getUrlsCount() {
		return urlsCount;
	}

	public void setUrlsCount(long urlsCount) {
		this.urlsCount = urlsCount;
	}

	public long getPendsCount() {
		return pendsCount;
	}

	public void setPendsCount(long pendsCount) {
		this.pendsCount = pendsCount;
	}

	public long getMetaCount() {
		return metaCount;
	}

	public void setMetaCount(long metaCount) {
		this.metaCount = metaCount;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Page> getPageList() {
		return pageList;
	}

	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public String getSeeds() {
		return seeds;
	}

	public void setSeeds(String seeds) {
		this.seeds = seeds;
	}

	public String getPatterns() {
		return patterns;
	}

	public void setPatterns(String patterns) {
		this.patterns = patterns;
	}

	public List<Seed> getSeedList() {
		return seedList;
	}

	public void setSeedList(List<Seed> seedList) {
		this.seedList = seedList;
	}

	public Seed getSeed() {
		return seed;
	}

	public void setSeed(Seed seed) {
		this.seed = seed;
	}

	public Map<String, String> getResolverMap() {
		return resolverMap;
	}

	public void setResolverMap(Map<String, String> resolverMap) {
		this.resolverMap = resolverMap;
	}

	public String getResolverName() {
		return resolverName;
	}

	public void setResolverName(String resolverName) {
		this.resolverName = resolverName;
	}

	public String getResolverClazz() {
		return resolverClazz;
	}

	public void setResolverClazz(String resolverClazz) {
		this.resolverClazz = resolverClazz;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(String extraParams) {
		this.extraParams = extraParams;
	}

	public ProductData getProductData() {
		return productData;
	}

	public void setProductData(ProductData productData) {
		this.productData = productData;
	}

	public String getSeedId() {
		return seedId;
	}

	public void setSeedId(String seedId) {
		this.seedId = seedId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStopPatterns() {
		return stopPatterns;
	}

	public void setStopPatterns(String stopPatterns) {
		this.stopPatterns = stopPatterns;
	}

	public List<JobVo> getJobVoList() {
		return jobVoList;
	}

	public void setJobVoList(List<JobVo> jobVoList) {
		this.jobVoList = jobVoList;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public JobVo getJobVo() {
		return jobVo;
	}

	public void setJobVo(JobVo jobVo) {
		this.jobVo = jobVo;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}

	public List<UrlRoad> getUrlRoadList() {
		return urlRoadList;
	}

	public void setUrlRoadList(List<UrlRoad> urlRoadList) {
		this.urlRoadList = urlRoadList;
	}

	public Map<String, List<UrlRoad>> getUrlRoadMap() {
		return urlRoadMap;
	}

	public void setUrlRoadMap(Map<String, List<UrlRoad>> urlRoadMap) {
		this.urlRoadMap = urlRoadMap;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCrawlInitCount() {
		return crawlInitCount;
	}

	public void setCrawlInitCount(long crawlInitCount) {
		this.crawlInitCount = crawlInitCount;
	}

	public long getCrawlQueCount() {
		return crawlQueCount;
	}

	public void setCrawlQueCount(long crawlQueCount) {
		this.crawlQueCount = crawlQueCount;
	}

	public long getCrawlErrCount() {
		return crawlErrCount;
	}

	public void setCrawlErrCount(long crawlErrCount) {
		this.crawlErrCount = crawlErrCount;
	}

	public long getCrawl400Count() {
		return crawl400Count;
	}

	public void setCrawl400Count(long crawl400Count) {
		this.crawl400Count = crawl400Count;
	}

	public long getCrawl500Count() {
		return crawl500Count;
	}

	public void setCrawl500Count(long crawl500Count) {
		this.crawl500Count = crawl500Count;
	}

	public long getTaskInitCount() {
		return taskInitCount;
	}

	public void setTaskInitCount(long taskInitCount) {
		this.taskInitCount = taskInitCount;
	}

	public long getTaskQueCount() {
		return taskQueCount;
	}

	public void setTaskQueCount(long taskQueCount) {
		this.taskQueCount = taskQueCount;
	}

	public long getTaskSucCount() {
		return taskSucCount;
	}

	public void setTaskSucCount(long taskSucCount) {
		this.taskSucCount = taskSucCount;
	}

	public long getTaskErrCount() {
		return taskErrCount;
	}

	public void setTaskErrCount(long taskErrCount) {
		this.taskErrCount = taskErrCount;
	}

	public long getTaskNoTaskCount() {
		return taskNoTaskCount;
	}

	public void setTaskNoTaskCount(long taskNoTaskCount) {
		this.taskNoTaskCount = taskNoTaskCount;
	}

	public long getTaskNoJobCount() {
		return taskNoJobCount;
	}

	public void setTaskNoJobCount(long taskNoJobCount) {
		this.taskNoJobCount = taskNoJobCount;
	}

	public long getTaskNoPageCount() {
		return taskNoPageCount;
	}

	public void setTaskNoPageCount(long taskNoPageCount) {
		this.taskNoPageCount = taskNoPageCount;
	}

	public long getTaskNoFileCount() {
		return taskNoFileCount;
	}

	public void setTaskNoFileCount(long taskNoFileCount) {
		this.taskNoFileCount = taskNoFileCount;
	}

	public long getCrawlSucCount() {
		return crawlSucCount;
	}

	public void setCrawlSucCount(long crawlSucCount) {
		this.crawlSucCount = crawlSucCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Urls> getUrlsList() {
		return urlsList;
	}

	public void setUrlsList(List<Urls> urlsList) {
		this.urlsList = urlsList;
	}

	public List<Pends> getPendsList() {
		return pendsList;
	}

	public void setPendsList(List<Pends> pendsList) {
		this.pendsList = pendsList;
	}

	public Map<String, Long> getUrlDataCountMap() {
		return urlDataCountMap;
	}

	public void setUrlDataCountMap(Map<String, Long> urlDataCountMap) {
		this.urlDataCountMap = urlDataCountMap;
	}

	public Map<String, Integer> getUrlsStatusNameMap() {
		Map<String, Integer> ret = new LinkedHashMap<String, Integer>();
		for (UrlsStatusEnum statusEnum : UrlsStatusEnum.values()) {
			ret.put(statusEnum.name(), statusEnum.getStatus());
		}
		return ret;
	}

	public Map<String, String> getUrlsStatusCodeMap() {
		Map<String, String> ret = new LinkedHashMap<String, String>();
		for (UrlsStatusEnum statusEnum : UrlsStatusEnum.values()) {
			ret.put(String.valueOf(statusEnum.getStatus()), statusEnum.name());
		}
		return ret;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Pends getPends() {
		return pends;
	}

	public void setPends(Pends pends) {
		this.pends = pends;
	}

	public int getRunningCount() {
		return runningCount;
	}

	public void setRunningCount(int runningCount) {
		this.runningCount = runningCount;
	}

	public int getStoppingCount() {
		return stoppingCount;
	}

	public void setStoppingCount(int stoppingCount) {
		this.stoppingCount = stoppingCount;
	}

	public int getJobCount() {
		return jobCount;
	}

	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}

	public long getJobMetaCount() {
		return jobMetaCount;
	}

	public void setJobMetaCount(long jobMetaCount) {
		this.jobMetaCount = jobMetaCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public int getDisableCount() {
		return disableCount;
	}

	public void setDisableCount(int disableCount) {
		this.disableCount = disableCount;
	}

	public int getEnableCount() {
		return enableCount;
	}

	public void setEnableCount(int enableCount) {
		this.enableCount = enableCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<SiteConfig> getSiteConfigList() {
		return siteConfigList;
	}

	public void setSiteConfigList(List<SiteConfig> siteConfigList) {
		this.siteConfigList = siteConfigList;
	}

	public String getNewSeed() {
		return newSeed;
	}

	public void setNewSeed(String newSeed) {
		this.newSeed = newSeed;
	}

	public String getNewParam() {
		return newParam;
	}

	public void setNewParam(String newParam) {
		this.newParam = newParam;
	}

	public PageVo getPageVo() {
		return pageVo;
	}

	public void setPageVo(PageVo pageVo) {
		this.pageVo = pageVo;
	}

	public List<PageVo> getPageVoList() {
		return pageVoList;
	}

	public void setPageVoList(List<PageVo> pageVoList) {
		this.pageVoList = pageVoList;
	}

	public String getNewCharset() {
		return newCharset;
	}

	public void setNewCharset(String newCharset) {
		this.newCharset = newCharset;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public List<String> getCatList() {
		return catList;
	}

	public void setCatList(List<String> catList) {
		this.catList = catList;
	}

}
