package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class Seed implements MongoEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3841523248615228757L;
	private String id;
	private String jobId;
	private String url;
	private String charset;
	private String params;
	private int status;
	private Date creationDate;

	public enum StatusEnum {
		ENABLE(0), //
		DISABLE(1), //
		;
		private final int status;

		StatusEnum(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}

		public static StatusEnum parseUrlsStatus(Integer status) {
			if(status == null) {
				return null;
			}
			for (StatusEnum st : StatusEnum.values()) {
				if (status == st.getStatus()) {
					return st;
				}
			}
			return null;
		}
	}

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

	@Column(name = "job_id")
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "c_date")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "params")
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "charset")
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
