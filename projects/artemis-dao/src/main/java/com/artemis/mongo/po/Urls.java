package com.artemis.mongo.po;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class Urls implements MongoEntity {

	public enum UrlsStatusEnum {
		CRAWL_INIT(0), //
		CRAWL_QUE(1), //
		CRAWL_SUC(2), //
		CRAWL_ERR(3), //
		CRAWL_400(4), //
		CRAWL_500(5), //

		TASK_INIT(6), //
		TASK_QUE(7), //
		TASK_SUC(8), //
		TASK_ERR(9), //
		TASK_NO_JOB(10), //
		TASK_NO_PAGE(11), //
		TASK_NO_TASK(12), //
		TASK_NO_FILE(13), //
		;
		private final int status;

		UrlsStatusEnum(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}

		public static UrlsStatusEnum parseUrlsStatus(int status) {
			for (UrlsStatusEnum st : UrlsStatusEnum.values()) {
				if (status == st.getStatus()) {
					return st;
				}
			}
			return null;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4140400383668672107L;

	public static final int CRAWL_INIT = UrlsStatusEnum.CRAWL_INIT.getStatus();
	public static final int CRAWL_QUE = UrlsStatusEnum.CRAWL_QUE.getStatus();
	public static final int CRAWL_SUC = UrlsStatusEnum.CRAWL_SUC.getStatus();
	public static final int CRAWL_ERR = UrlsStatusEnum.CRAWL_ERR.getStatus();
	public static final int CRAWL_400 = UrlsStatusEnum.CRAWL_400.getStatus();
	public static final int CRAWL_500 = UrlsStatusEnum.CRAWL_500.getStatus();

	public static final int TASK_INIT = UrlsStatusEnum.TASK_INIT.getStatus();
	public static final int TASK_QUE = UrlsStatusEnum.TASK_QUE.getStatus();
	public static final int TASK_SUC = UrlsStatusEnum.TASK_SUC.getStatus();
	public static final int TASK_ERR = UrlsStatusEnum.TASK_ERR.getStatus();
	public static final int TASK_NO_JOB = UrlsStatusEnum.TASK_NO_JOB.getStatus();
	public static final int TASK_NO_PAGE = UrlsStatusEnum.TASK_NO_PAGE.getStatus();
	public static final int TASK_NO_TASK = UrlsStatusEnum.TASK_NO_TASK.getStatus();
	public static final int TASK_NO_FILE = UrlsStatusEnum.TASK_NO_FILE.getStatus();

	private String id;
	private int status; // 0-待抓取 1-抓取队列中 2-抓取成功 3-抓取错误 4-400错误 5-500错误 6-等待任务处理
						// 7-任务队列中
	private String jobId;
	private String charset;
	private String referer;
	private int errors;
	private int statusCode; // URL请求状态码
	private Map<String, String> params;
	private Date creationDate;
	private String root;
	private String sessionId;
	private int priority;// 优先级 0-5递增,数字越大优先级越高

	@Id
	@Column(name = ID)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@Column(name = "referer")
	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	@Column(name = "job_id")
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "errors")
	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	@Column(name = "params")
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Column(name = "status_code")
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Column(name = "root")
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Column(name = "priority")
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Column(name = "session_id")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
