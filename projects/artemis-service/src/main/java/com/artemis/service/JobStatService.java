package com.artemis.service;

import java.util.Date;

import com.artemis.mongo.dao.JobStatDao;
import com.artemis.mongo.po.JobStat;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JobStatService {
	private static JobStatService instance = new JobStatService();
	JobStatDao jobStatDao = JobStatDao.getInstance();

	private JobStatService() {

	}

	public static JobStatService getInstance() {
		return instance;
	}

	public void startJobStat(String jobId, String sessionId) {
		JobStat jobStat = new JobStat();
		jobStat.setJobId(jobId);
		jobStat.setSessionId(sessionId);
		jobStat.setStartDate(new Date());

		jobStat.setCrawlCount(0);
		jobStat.setTaskCount(0);
		jobStat.setErrCount(0);
		jobStat.setMetaCount(0);
		jobStat.setPubCount(0);

		jobStat.setCreationDate(new Date());

		this.jobStatDao.save(jobStat);
	}

	public void endJobStat(String jobId, String sessionId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("session_id", sessionId);
		DBObject data = new BasicDBObject();
		data.put("end_date", new Date());
		this.jobStatDao.update(data, q);
	}

	public void increaseCrawlCount(String jobId, String sessionId) {
		JobService.getInstance().increaseCrawlCount(jobId, sessionId);
	}

	public void increaseTaskCount(String jobId, String sessionId) {
		JobService.getInstance().increaseTaskCount(jobId, sessionId);
	}

	public void increaseErrCount(String jobId, String sessionId) {
		JobService.getInstance().increaseErrCount(jobId, sessionId);
	}

	public void increaseMetaCount(String jobId, String sessionId) {
		JobService.getInstance().increaseMetaCount(jobId, sessionId);
	}

}
