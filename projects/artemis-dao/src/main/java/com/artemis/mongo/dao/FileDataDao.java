package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.FileData;

public class FileDataDao extends MongoDaoImpl<FileData> implements MongoDao<FileData>, DBConfigHolder {
	private static FileDataDao instance = new FileDataDao();

	private FileDataDao() {

	}

	public static FileDataDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "file_data");
	}

}
