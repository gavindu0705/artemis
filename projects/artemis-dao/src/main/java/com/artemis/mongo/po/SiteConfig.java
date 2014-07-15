package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class SiteConfig implements MongoEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6923598350823335406L;
	private String id;
	private String name;
	private String root;
	private String charset;
	private int cloudRate;
	private int shotRate;
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

	@Column(name = "c_date")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "charset")
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "root")
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Column(name = "shot_rate")
	public int getShotRate() {
		return shotRate;
	}

	public void setShotRate(int shotRate) {
		this.shotRate = shotRate;
	}

	@Column(name = "cloud_rate")
	public int getCloudRate() {
		return cloudRate;
	}

	public void setCloudRate(int cloudRate) {
		this.cloudRate = cloudRate;
	}

}
