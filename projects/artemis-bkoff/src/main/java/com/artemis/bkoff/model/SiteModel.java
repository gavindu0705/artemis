package com.artemis.bkoff.model;

import java.util.List;

import com.artemis.mongo.po.SiteConfig;

public class SiteModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7352518876695231665L;

	private String id;
	private SiteConfig siteConfig;
	private List<SiteConfig> siteConfigList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public void setSiteConfig(SiteConfig siteConfig) {
		this.siteConfig = siteConfig;
	}

	public List<SiteConfig> getSiteConfigList() {
		return siteConfigList;
	}

	public void setSiteConfigList(List<SiteConfig> siteConfigList) {
		this.siteConfigList = siteConfigList;
	}

}
