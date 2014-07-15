package com.artemis.mongo.dao;

import java.util.List;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Func;

public class FuncDao extends MongoDaoImpl<Func> implements MongoDao<Func>, DBConfigHolder {
	private static FuncDao instance = new FuncDao();
	private List<Func> funcList = null;

	private FuncDao() {
		super();
		funcList = super.findAll();
	}

	public static FuncDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "func");
	}

	public Func findFuncByClazz(String clazz) {
		for (Func func : funcList) {
			if (func.getClazz().equals(clazz)) {
				return func;
			}
		}
		return null;
	}
	
}
