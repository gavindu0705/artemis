package com.artemis.bkoff.model;

public class IndexModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2232700357641851210L;

	private long urlsCount;
	private long pendsCount;
	private long metaCount;

	public long getUrlsCount() {
		return urlsCount;
	}

	public void setUrlsCount(long urlsCount) {
		this.urlsCount = urlsCount;
	}

	public long getPendsCount() {
		return pendsCount;
	}

	public void setPendsCount(long pendsCount) {
		this.pendsCount = pendsCount;
	}

	public long getMetaCount() {
		return metaCount;
	}

	public void setMetaCount(long metaCount) {
		this.metaCount = metaCount;
	}

}
