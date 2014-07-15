package com.artemis.mongo.dao;

import java.util.Date;
import java.util.List;

import com.artemis.core.bean.Proxy;
import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.HttpAdsl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class HttpAdslDao extends MongoDaoImpl<HttpAdsl> implements MongoDao<HttpAdsl>, DBConfigHolder {
	private static HttpAdslDao instance = new HttpAdslDao();

	private HttpAdslDao() {
		super();
	}

	public static HttpAdslDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "http_adsl");
	}

	public List<HttpAdsl> findHttpAdsl() {
		return this.findAll();
	}

	public void updateToRestarting(String id) {
		DBObject data = new BasicDBObject();
		data.put("status", Proxy.RESTARTING);
		this.updateById(data, id);
	}

	public void updateToRestarted(String id, int status, String publicIp) {
		DBObject data = new BasicDBObject();
		data.put("status", status);
		data.put("public_ip", publicIp);
		data.put("restart_date", new Date());
		this.updateById(data, id);
	}

	public void updateToError(String id) {
		DBObject data = new BasicDBObject();
		data.put("status", Proxy.ERROR);
		this.updateById(data, id);
	}
}
