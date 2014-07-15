package com.artemis.bkoff.model;

import org.sothis.web.mvc.ModelAndViewSupport;

public class JsonModel extends ModelAndViewSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2814639689394985415L;
	private Object jsonModel;

	public JsonModel() {
		super();
		this.setViewType("json");
	}

	public Object model() {
		return jsonModel;
	}

	public void setJsonModel(Object model) {
		jsonModel = model;
	}
}
