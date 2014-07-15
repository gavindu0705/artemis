package com.artemis.mongo.dao;

import java.util.Date;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.RoadMap;
import com.mongodb.BasicDBObject;

public class RoadMapDao_bak extends MongoDaoImpl<RoadMap> implements MongoDao<RoadMap>, DBConfigHolder {
	private static RoadMapDao_bak instance = new RoadMapDao_bak();

	public static final String[] ID_FIELDS = { "_id" };

	private RoadMapDao_bak() {

	}

	public static RoadMapDao_bak getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "road_map");
	}

	/**
	 * 是否在该任务中已经存在或处理过
	 * 
	 * @param url
	 * @param jobId
	 * @return
	 */
	public boolean isFootprint(String url, String jobId) {
		if (url == null || jobId == null) {
			return false;
		}
		RoadMap obj = this.findById(idGenerator(url, jobId), ID_FIELDS);
		if (obj != null) {
			return true;
		}
		return false;
	}

	public void saveRoadMap(String url, String jobId) {
		RoadMap roadMap = new RoadMap();
		roadMap.setId(idGenerator(url, jobId));
		roadMap.setUrl(url);
		roadMap.setJobId(jobId);
		roadMap.setCreationDate(new Date());
		this.save(roadMap);
	}

	private String idGenerator(String url, String jobId) {
		return url + jobId;
	}

	public void cleanRoadMap(String jobId) {
		this.delete(new BasicDBObject("job_id", jobId));
	}
}
