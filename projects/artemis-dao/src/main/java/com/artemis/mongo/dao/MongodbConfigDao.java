package com.artemis.mongo.dao;

import javax.persistence.Table;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DB;
import com.artemis.mongo.persistence.HostPort;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.MongodbConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@HostPort(host = "artemis.db.fangjia.com", port = 55007)
@DB(name = "artemis")
@Table(name = "mongodb_config")
public class MongodbConfigDao extends MongoDaoImpl<MongodbConfig> implements MongoDao<MongodbConfig> {
	private static MongodbConfigDao instance = new MongodbConfigDao();
//	private static Map<String, DBCollection> collectionMap = new HashMap<String, DBCollection>();

	public static MongodbConfigDao getInstance() {
		return instance;
	}

	public Conf getConf(String db, String collection) {
		DBObject q = new BasicDBObject();
		q.put("db", db);
		q.put("collection", collection);
		MongodbConfig mc = this.findOne(q);
		if (mc == null) {
			return null;
		}
		return new Conf(mc.getHost(), mc.getPort(), mc.getDb(), mc.getCollection());
	}

//	public DBCollection getCollection(String db, String collection) {
//		try {
//			String key = db + "-" + collection;
//			Conf conf = getConf(db, collection);
//			if (!collectionMap.containsKey(key)) {
//				Mongo mongo = new Mongo(conf.getHost(), conf.getPort());
//				collectionMap.put(key, mongo.getDB(db).getCollection(collection));
//			}
//			return collectionMap.get(key);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (MongoException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
