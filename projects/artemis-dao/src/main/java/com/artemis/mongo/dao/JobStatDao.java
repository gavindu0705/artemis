package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.JobStat;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JobStatDao extends MongoDaoImpl<JobStat> implements MongoDao<JobStat>, DBConfigHolder {
	private static JobStatDao instance = new JobStatDao();

	private JobStatDao() {

	}

	public static JobStatDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "job_stat");
	}

	public JobStat findJobStat(String jobId, String sessionId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("session_id", sessionId);
		return this.findOne(q);
	}
}
