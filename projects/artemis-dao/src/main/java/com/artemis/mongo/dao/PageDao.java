package com.artemis.mongo.dao;

import java.util.List;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Page;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PageDao extends MongoDaoImpl<Page> implements MongoDao<Page>, DBConfigHolder {
	private static PageDao instance = new PageDao();

	private PageDao() {

	}

	public static PageDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "page");
	}

	public List<Page> findPageByJobId(String jobId) {
		return this.findAll(new BasicDBObject("job_id", jobId), new BasicDBObject("c_date", 1));
	}

//	public void updatePageStop(String id) {
//		DBObject data = new BasicDBObject();
//		data.put("status", Page.TERMINAL);
//		this.updateById(data, id);
//	}
//
//	public void updatePageNormalByJobId(String jobId) {
//		DBObject data = new BasicDBObject();
//		data.put("status", Page.NORMAL);
//		this.update(data, new BasicDBObject("job_id", jobId));
//	}

	public void deleteByJobId(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		this.delete(q);
	}

}
