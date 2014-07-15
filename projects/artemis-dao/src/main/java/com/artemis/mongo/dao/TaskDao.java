package com.artemis.mongo.dao;

import java.util.List;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Task;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TaskDao extends MongoDaoImpl<Task> implements MongoDao<Task>, DBConfigHolder {
	private static TaskDao instance = new TaskDao();

	private TaskDao() {

	}

	public static TaskDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "task");
	}

	public List<Task> findTaskByJobPageId(String jobId, String pageId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		q.put("page_id", pageId);
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return this.findAll(q, sq);
	}

	public void deleteByJobId(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		this.delete(q);
	}

	public void deleteByPageId(String pageId) {
		DBObject q = new BasicDBObject();
		q.put("page_id", pageId);
		this.delete(q);
	}
}
