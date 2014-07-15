package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;

import com.artemis.core.bean.Proxy;
import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class GrandCloud implements MongoEntity, Proxy {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5080408282852831135L;
	private String id;
	private String url;
	private int port;
	private int freq; // 请求频次(m)
	private int status; // 0-正常 1-禁用 2-使用中
	private int mutis; // 0-不支持 1-支持 并发请求
	private String contextPath;
	private String params;
	private Date visitedDate;
	private Date creationDate;

	@Id
	@GeneratedValue
	@Column(name = ID)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "port")
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Column(name = "context_path")
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Column(name = "params")
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "c_date")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "freq")
	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "v_date")
	public Date getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(Date visitedDate) {
		this.visitedDate = visitedDate;
	}

	@Column(name = "mutis")
	public int getMutis() {
		return mutis;
	}

	public void setMutis(int mutis) {
		this.mutis = mutis;
	}

	@Override
	public String getSimpleName() {
		return StringUtils.substringBetween(this.url, "http://", ".");
	}
}
