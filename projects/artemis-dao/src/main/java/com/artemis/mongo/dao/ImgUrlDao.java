package com.artemis.mongo.dao;

import java.util.Date;
import java.util.List;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.ImgUrl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ImgUrlDao extends MongoDaoImpl<ImgUrl> implements MongoDao<ImgUrl>, DBConfigHolder {

	private static ImgUrlDao instance = new ImgUrlDao();

	private ImgUrlDao() {

	}

	public static ImgUrlDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "img_url");
	}

	/**
	 * ´ý×¥È¡µÄURL
	 * 
	 * @return
	 */
	public List<ImgUrl> findUrls(int status, int limit) {
		DBObject q = new BasicDBObject();
		q.put("status", 0);
		DBObject sq = new BasicDBObject();
		sq.put("c_date", 1);
		return this.findAll(q, sq, limit);
	}

	public void insert(String url, String referer) {
		ImgUrl imgUrl = new ImgUrl();
		imgUrl.setId(url);
		imgUrl.setReferer(referer);
		imgUrl.setStatus(0);
		imgUrl.setCreationDate(new Date());
		this.save(imgUrl);
	}

	public void updateToCrawled(String url) {
		DBObject data = new BasicDBObject();
		data.put("status", 2);
		this.updateById(data, url);
	}

	public void updateToError(String url) {
		DBObject data = new BasicDBObject();
		data.put("status", 1);
		this.updateById(data, url);
	}
}
