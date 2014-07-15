package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Job;

public class JobDao extends MongoDaoImpl<Job> implements MongoDao<Job>, DBConfigHolder {
	private static JobDao instance = new JobDao();

	private JobDao() {
	}

	public static JobDao getInstance() {
		return instance;
	}
	
	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "job");
	}
	
//	public void updateJobStatus(String id, int status) {
//		DBObject data = new BasicDBObject();
//		data.put("status", status);
//		this.updateById(data, id);
//	}

}
