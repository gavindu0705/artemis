package com.artemis.bkoff.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.artemis.bkoff.model.FuncModel;
import com.artemis.mongo.dao.FuncDao;
import com.artemis.mongo.po.Func;

public class FuncController {
	private FuncDao funcDao = FuncDao.getInstance();

	public Object listAction(FuncModel model) {
		model.setFuncList(funcDao.findAll());
		return model;
	}

	public Object createAction(FuncModel model) {
		return model;
	}

	public Object saveAction(FuncModel model) {
		Func entity = model.getFunc();
		entity.setCreationDate(new Date());
		if (model.getParams() != null && model.getParams().size() > 0) {
			List<String> list = new ArrayList<String>();
			for (String s : model.getParams()) {
				if (StringUtils.isNotBlank(s)) {
					if (!s.startsWith("$")) {
						s = "$" + s;
					}
					list.add(s);
				}
			}
			entity.setParams(list);
		}
		this.funcDao.save(entity);
		return model.redirect("/func/list");
	}

	public Object deleteAction(FuncModel model) {
		if (model.getId() != null) {
			funcDao.deleteById(model.getId());
		}
		return model.redirect("/func/list");
	}

}
