package com.artemis.mongo.dao;

import java.util.List;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Seed;
import com.artemis.mongo.po.Seed.StatusEnum;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SeedDao extends MongoDaoImpl<Seed> implements MongoDao<Seed>, DBConfigHolder {
	private static SeedDao instance = new SeedDao();

	private SeedDao() {

	}

	public static SeedDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "seed");
	}

	public long getCount(String jobId) {
		return findCount(new BasicDBObject("job_id", jobId));
	}

	public long getCount(String jobId, StatusEnum status) {
		return findCount(new BasicDBObject("job_id", jobId).append("status", status.getStatus()));
	}

	public List<Seed> findSeedByJobId(String jobId, int skip, int limit) {
		return find(new BasicDBObject("job_id", jobId), null, new BasicDBObject("c_date", 1), skip, limit);
	}

	public List<Seed> findSeedByJobId(String jobId, StatusEnum status, int skip, int limit) {
		return find(new BasicDBObject("job_id", jobId).append("status", status.getStatus()), null,
				new BasicDBObject("c_date", 1), skip, limit);
	}
	

	public List<Seed> findAllSeedByJobId(String jobId) {
		return findAll(new BasicDBObject("job_id", jobId), new BasicDBObject("c_date", 1));
	}

	public List<Seed> findAllSeedByJobId(String jobId, StatusEnum status) {
		return findAll(new BasicDBObject("job_id", jobId).append("status", status.getStatus()), new BasicDBObject("c_date", 1));
	}

	public Seed findSeedByUrl(String jobId, String url) {
		return findOne(new BasicDBObject("job_id", jobId).append("url", url));
	}

	public void updateSeedStatus(String jobId, StatusEnum status) {
		this.update(new BasicDBObject("status", status.getStatus()), new BasicDBObject("job_id", jobId));
	}

	public void updateSeedStatus(String jobId, String url, StatusEnum status) {
		this.update(new BasicDBObject("status", status.getStatus()), new BasicDBObject("job_id", jobId).append("url", url));
	}

	public void deleteByJobId(String jobId) {
		DBObject q = new BasicDBObject();
		q.put("job_id", jobId);
		this.delete(q);
	}

}
