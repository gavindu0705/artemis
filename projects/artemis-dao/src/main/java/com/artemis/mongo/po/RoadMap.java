package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class RoadMap implements MongoEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6816481840987426653L;
	private String id;
	private String url;
	private String jobId;
	private Date creationDate;
	
	@Id
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

	@Column(name = "job_id")
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
