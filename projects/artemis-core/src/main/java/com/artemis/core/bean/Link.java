package com.artemis.core.bean;

import java.util.Map;

/**
 * 访问链接对象
 * 
 * @author xiaoyu
 * 
 */
public class Link {

	private String url;
	private String referer;
	private Map<String, String> propertiesMap;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, String> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}
	
}
