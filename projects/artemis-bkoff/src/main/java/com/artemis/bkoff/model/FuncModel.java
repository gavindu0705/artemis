package com.artemis.bkoff.model;

import java.util.List;

import com.artemis.mongo.po.Func;

public class FuncModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4405543478557926498L;

	private String id;
	private Func func;
	private List<String> params;
	private List<Func> funcList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Func getFunc() {
		return func;
	}

	public void setFunc(Func func) {
		this.func = func;
	}

	public List<Func> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<Func> funcList) {
		this.funcList = funcList;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

}
