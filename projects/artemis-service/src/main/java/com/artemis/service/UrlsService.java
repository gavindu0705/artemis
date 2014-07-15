package com.artemis.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.artemis.core.bean.Harvest.HarvestStatusEnum;
import com.artemis.core.log.ALogger;
import com.artemis.mongo.dao.ErrsDao;
import com.artemis.mongo.dao.PendsDao;
import com.artemis.mongo.dao.UrlsDao;
import com.artemis.mongo.po.Errs;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Pends;
import com.artemis.mongo.po.Urls;
import com.artemis.mongo.po.Urls.UrlsStatusEnum;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * URL
 * 
 * @author xiaoyu
 * 
 */
public class UrlsService {
	private static UrlsService instance = new UrlsService();
	private ErrsDao errsDao = ErrsDao.getInstance();
	private UrlsDao urlsDao = UrlsDao.getInstance();
	private PendsDao pendsDao = PendsDao.getInstance();
	public static final ALogger LOG = new ALogger(UrlsService.class);

	private UrlsService() {

	}

	public static UrlsService getInstance() {
		return instance;
	}

	/**
	 * 插入需要抓取的URL
	 * 
	 * @param url
	 * @param referer
	 * @param charset
	 */
	public void addUrlsCrawlInit(String url, String referer, String charset, String jobId, String sessionId,
			Map<String, String> params) {
		if (url != null && jobId != null) {
			Job job = JobService.getInstance().getJobById(jobId);
			if (job == null) {
				return;
			}

			Urls entity = new Urls();
			entity.setId(url.trim());
			if (StringUtils.isNotBlank(charset)) {
				entity.setCharset(charset);
			}
			entity.setReferer(referer);
			entity.setStatus(0);
			entity.setJobId(jobId);
			entity.setSessionId(sessionId);
			entity.setParams(params);
			entity.setPriority(job.getPriority());
			entity.setSessionId(job.getSessionId());
			entity.setRoot(job.getRoot());
			entity.setCreationDate(new Date());
			urlsDao.save(entity);
			UrlRoadService.getInstance().addUrlRoad(jobId, sessionId, url, referer, UrlsStatusEnum.CRAWL_INIT);
		}
	}

	/**
	 * 按JOB_ID查询
	 * 
	 * @param jobId
	 * @param limit
	 * @return
	 */
	public List<Urls> findUrlsByJobId(String jobId, int limit) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		return urlsDao.findAll(q, null, limit);
	}

	public List<Urls> findUrlsByJobId(String jobId, UrlsStatusEnum statusEnum, int limit) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", statusEnum.getStatus());
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return urlsDao.findAll(q, sq, limit);
	}

	/**
	 * 取出待抓取的URL
	 * 
	 * @param limit
	 * @return
	 */
	public List<Urls> findUrlsCrawlInit(String root, int priority, int limit) {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.CRAWL_INIT);
		q.put("root", root);
		q.put("priority", priority);
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return urlsDao.findAll(q, sq, limit);
	}

	/**
	 * 待抓取的URL数量
	 * 
	 * @return
	 */
	public long findUrlsCrawlInitCount() {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.CRAWL_INIT);
		return urlsDao.findCount(q);
	}

	public long findUrlsCrawlInitCount(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", Urls.CRAWL_INIT);
		return urlsDao.findCount(q);
	}

	/**
	 * 取出抓取成功的URL
	 * 
	 * @param status
	 * @param limit
	 * @return
	 */
	public List<Urls> findUrlsCrawlSuc(int limit) {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.CRAWL_SUC);
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return urlsDao.findAll(q, sq, limit);
	}

	/**
	 * 取出需要被重新抓取的URL
	 * 
	 * @param limit
	 * @return
	 */
	public List<Urls> findUrlsCrawlErr(int limit) {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.CRAWL_ERR);
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return urlsDao.findAll(q, sq, limit);
	}

	/**
	 * 更新URL状态为抓取排队中
	 * 
	 * @param id
	 */
	public void updateUrlsCrawlQue(String id) {
		DBObject data = new BasicDBObject();
		data.put("status", Urls.CRAWL_QUE);
		urlsDao.updateById(data, id);
	}

	/**
	 * 更新状态为抓取成功
	 * 
	 * @param id
	 */
	public void updateUrlsCrawlSuc(String id) {
		Urls urls = this.urlsDao.findById(id);
		if (urls != null) {
			DBObject data = new BasicDBObject();
			data.put("status", Urls.CRAWL_SUC);
			data.put("status_code", HarvestStatusEnum.SUCCESS.getCode());
			urlsDao.updateById(data, id);

			// 记录抓取成功日志
			JobStatService.getInstance().increaseCrawlCount(urls.getJobId(), urls.getSessionId());
			UrlRoadService.getInstance().addUrlRoad(urls.getJobId(), urls.getSessionId(), urls.getId(), urls.getReferer(),
					UrlsStatusEnum.CRAWL_SUC);
		}
	}

	/**
	 * 更新状态为抓取错误
	 * 
	 * @param id
	 */
	public void updateUrlsCrawlErr(String id, HarvestStatusEnum statusCode) {
		Urls obj = urlsDao.findById(id);
		if (obj != null) {
			DBObject data = new BasicDBObject();
			data.put("status", Urls.CRAWL_ERR);
			data.put("errors", obj.getErrors() + 1);
			if (statusCode != null) {
				data.put("status_code", statusCode.getCode());
			}
			urlsDao.updateById(data, id);
			// 记录日志
			JobStatService.getInstance().increaseErrCount(obj.getJobId(), obj.getSessionId());
			UrlRoadService.getInstance().addUrlRoad(obj.getJobId(), obj.getSessionId(), obj.getId(), obj.getReferer(),
					UrlsStatusEnum.CRAWL_ERR);
		}
	}

	/**
	 * 修改为待抓取，并且增加出错次数
	 * 
	 * @param id
	 */
	public void updateUrlsCrawlErrToInit(String id, int errorCount) {
		DBObject data = new BasicDBObject();
		data.put("status", Urls.CRAWL_INIT);
		data.put("errors", errorCount + 1);
		urlsDao.updateById(data, id);
	}

	/**
	 * URL抓取待处理更新到抓取初始化
	 */
	public void updateUrlsCrawlQueToInit() {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.CRAWL_QUE);
		DBObject data = new BasicDBObject();
		data.put("status", Urls.CRAWL_INIT);
		urlsDao.update(data, q);
	}

	public void deleteUrlsById(String id) {
		this.urlsDao.deleteById(id);
	}

	public void deletePendsById(String id) {
		this.pendsDao.deleteById(id);
	}

	public void deleteErrsById(String id) {
		this.errsDao.deleteById(id);
	}

	/**
	 * 按任务删除
	 * 
	 * @param jobId
	 */
	public void deleteUrlsByJobId(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		urlsDao.delete(q);
	}

	public List<Pends> findPendsTaskInit(int limit) {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.TASK_INIT);
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return pendsDao.findAll(q, sq, limit);
	}

	/**
	 * 待处理的URL数量
	 * 
	 * @return
	 */
	public long findPendsTaskInitCount() {
		DBObject q = new BasicDBObject();
		q.put("status", Urls.TASK_INIT);
		return pendsDao.findCount(q);
	}

	public long findPendsTaskInitCount(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", Urls.TASK_INIT);
		return pendsDao.findCount(q);
	}

	public List<Pends> findPendsByJobId(String jobId, int limit) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		return pendsDao.findAll(q, null, limit);
	}

	public List<Pends> findPendsByJobId(String jobId, UrlsStatusEnum statusEnum, int limit) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", statusEnum.getStatus());
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return pendsDao.findAll(q, sq, limit);
	}

	public List<Pends> findPendsByJobIdStatus(String jobId, int status, int limit) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", status);
		return pendsDao.findAll(q, null, limit);
	}

	/**
	 * URL任务待处理更新到任务初始化
	 */
	public void updatePendsTaskQueToInit() {
		DBObject q = new BasicDBObject();
		q.put("status", Pends.TASK_QUE);
		DBObject data = new BasicDBObject();
		data.put("status", Pends.TASK_INIT);
		pendsDao.update(data, q);
	}

	/**
	 * 更新URL状态为任务处理排队中
	 * 
	 * @param id
	 */
	public void updatePendsTaskQue(String id) {
		DBObject data = new BasicDBObject();
		data.put("status", Pends.TASK_QUE);
		pendsDao.updateById(data, id);
	}

	public void updatePendsTaskSuc(String id) {
		// DBObject data = new BasicDBObject();
		// data.put("status", Pends.TASK_SUC);
		// pendsDao.updateById(data, id);
		Pends pends = this.pendsDao.findById(id);
		if (pends != null) {
			// 记录任务处理日志
			JobStatService.getInstance().increaseTaskCount(pends.getJobId(), pends.getSessionId());
			UrlRoadService.getInstance().addUrlRoad(pends.getJobId(), pends.getSessionId(), pends.getId(), pends.getReferer(),
					UrlsStatusEnum.TASK_SUC);
			pendsDao.deleteById(id);
		}
	}

	public void pendsToErrsTaskErr(Pends pends) {
		pends.setStatus(Pends.TASK_ERR);
		this.pendsToErrs(pends);
	}

	public void pendsToErrsTaskNOFILE(Pends pends) {
		pends.setStatus(Pends.TASK_NO_FILE);
		this.pendsToErrs(pends);
	}

	public void pendsToErrsTaskNOJOB(Pends pends) {
		pends.setStatus(Pends.TASK_NO_JOB);
		this.pendsToErrs(pends);
	}

	public void pendsToErrsTaskNOPAGE(Pends pends) {
		pends.setStatus(Pends.TASK_NO_PAGE);
		this.pendsToErrs(pends);
	}

	public void pendsToErrsTaskNOTASK(Pends pends) {
		pends.setStatus(Pends.TASK_NO_TASK);
		this.pendsToErrs(pends);
	}

	public void deletePendsByJobId(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		pendsDao.delete(q);
	}

	public Pends findPendsById(String id) {
		return this.pendsDao.findById(id);
	}

	/**
	 * urls转换成pends
	 * 
	 * @param urls
	 */
	public void urlsToPends(Urls urls) {
		if (urls == null || urls.getId() == null) {
			return;
		}

		try {
			Pends pends = new Pends();
			BeanUtils.copyProperties(pends, urls);
			pends.setStatus(Pends.TASK_INIT);
			pendsDao.save(pends);
			deleteUrlsById(urls.getId());
		} catch (Exception e) {
			LOG.error("", e);
		}
	}

	/**
	 * 转换URL为ERRS
	 * 
	 * @param urls
	 */
	public void urlsToErrs(Urls urls) {
		if (urls == null || urls.getId() == null) {
			return;
		}

		try {
			Errs errs = new Errs();
			BeanUtils.copyProperties(errs, urls);
			errsDao.save(errs);
			this.deleteUrlsById(urls.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转换pends为Errs
	 * 
	 * @param pends
	 */
	public void pendsToErrs(Pends pends) {
		if (pends == null || pends.getId() == null) {
			return;
		}

		try {
			Errs errs = new Errs();
			BeanUtils.copyProperties(errs, pends);
			errsDao.save(errs);
			this.deletePendsById(pends.getId());

			UrlRoadService.getInstance().addUrlRoad(pends.getJobId(), pends.getSessionId(), pends.getId(), pends.getReferer(),
					UrlsStatusEnum.TASK_ERR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long findUrlsCount(UrlsStatusEnum statusEnum) {
		DBObject q = new BasicDBObject();
		q.put("status", statusEnum.getStatus());
		return urlsDao.findCount(q);
	}

	public long findUrlsCount(String jobId, UrlsStatusEnum statusEnum) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", statusEnum.getStatus());
		return urlsDao.findCount(q);
	}

	public long findPendsCount(UrlsStatusEnum statusEnum) {
		DBObject q = new BasicDBObject();
		q.put("status", statusEnum.getStatus());
		return pendsDao.findCount(q);
	}

	public long findPendsCount(String jobId, UrlsStatusEnum statusEnum) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", statusEnum.getStatus());
		return pendsDao.findCount(q);
	}

	public long findErrsCount(UrlsStatusEnum statusEnum) {
		DBObject q = new BasicDBObject();
		q.put("status", statusEnum.getStatus());
		return errsDao.findCount(q);
	}

	public long findErrsCount(String jobId, UrlsStatusEnum statusEnum) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("status", statusEnum.getStatus());
		return errsDao.findCount(q);
	}

}
