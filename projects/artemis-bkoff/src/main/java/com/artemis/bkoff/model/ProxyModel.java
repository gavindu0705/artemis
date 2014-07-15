package com.artemis.bkoff.model;

import java.util.List;

import com.artemis.mongo.po.GrandCloud;
import com.artemis.mongo.po.HttpAdsl;

public class ProxyModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -272436074419282852L;

	private String id;

	private int status;

	private String ip;

	private HttpAdsl httpAdsl;


	private GrandCloud grandCloud;

	private List<HttpAdsl> httpAdslList;

	private List<GrandCloud> grandCloudList;

	public List<HttpAdsl> getHttpAdslList() {
		return httpAdslList;
	}

	public void setHttpAdslList(List<HttpAdsl> httpAdslList) {
		this.httpAdslList = httpAdslList;
	}

	public List<GrandCloud> getGrandCloudList() {
		return grandCloudList;
	}

	public void setGrandCloudList(List<GrandCloud> grandCloudList) {
		this.grandCloudList = grandCloudList;
	}

	public HttpAdsl getHttpAdsl() {
		return httpAdsl;
	}

	public void setHttpAdsl(HttpAdsl httpAdsl) {
		this.httpAdsl = httpAdsl;
	}

	public GrandCloud getGrandCloud() {
		return grandCloud;
	}

	public void setGrandCloud(GrandCloud grandCloud) {
		this.grandCloud = grandCloud;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
