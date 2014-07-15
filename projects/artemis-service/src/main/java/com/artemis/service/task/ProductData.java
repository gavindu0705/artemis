package com.artemis.service.task;

import java.util.List;

import com.artemis.mongo.po.Metadata;
import com.artemis.mongo.po.Page;
import com.artemis.mongo.po.Urls;

public class ProductData {
	private String url;
	private Page matchPage;
	private int status;
	private List<Urls> urls;
	private Metadata metadata;

	public List<Urls> getUrls() {
		return urls;
	}

	public void setUrls(List<Urls> urls) {
		this.urls = urls;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Page getMatchPage() {
		return matchPage;
	}

	public void setMatchPage(Page matchPage) {
		this.matchPage = matchPage;
	}

}
