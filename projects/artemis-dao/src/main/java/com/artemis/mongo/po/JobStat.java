package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class JobStat implements MongoEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1923188645588492734L;

	private String id;
	private String jobId;
	private String sessionId;
	private int crawlCount;
	private int taskCount;
	private int errCount;
	private int metaCount;
	private int pubCount;
	private Date startDate;
	private Date endDate;
	private Date creationDate;

	public JobStat() {

	}

	public JobStat(String jobId, String sessionId, int crawlCount, int taskCount, int errCount, int metaCount) {
		this.jobId = jobId;
		this.sessionId = sessionId;
		this.crawlCount = crawlCount;
		this.taskCount = taskCount;
		this.errCount = errCount;
		this.metaCount = metaCount;
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

	@Column(name = "session_id")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "crawl_count")
	public int getCrawlCount() {
		return crawlCount;
	}

	public void setCrawlCount(int crawlCount) {
		this.crawlCount = crawlCount;
	}

	@Column(name = "task_count")
	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	@Column(name = "err_count")
	public int getErrCount() {
		return errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "pub_count")
	public int getPubCount() {
		return pubCount;
	}

	public void setPubCount(int pubCount) {
		this.pubCount = pubCount;
	}

	@Column(name = "meta_count")
	public int getMetaCount() {
		return metaCount;
	}

	public void setMetaCount(int metaCount) {
		this.metaCount = metaCount;
	}

}
