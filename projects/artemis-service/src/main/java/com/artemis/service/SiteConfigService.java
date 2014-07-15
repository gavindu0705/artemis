package com.artemis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.artemis.core.tools.AsyncThread;
import com.artemis.core.tools.AsyncThread.Call;
import com.artemis.mongo.dao.SiteConfigDao;
import com.artemis.mongo.po.SiteConfig;

public class SiteConfigService {
	private static SiteConfigService instance = new SiteConfigService();
	private SiteConfigDao siteConfigDao = SiteConfigDao.getInstance();
	private Map<String, SiteConfig> siteConfigMap = new HashMap<String, SiteConfig>();
	public static final String DEFAULT_ROOT = "www";

	public static final String[] ROOT_DOMAINS = { ".com.cn", ".net.cn", ".org.cn", ".gov.cn", ".com", ".cn", ".co", ".net",
			".org", ".me", ".biz", ".name", ".info", ".so", ".tel", ".mobi", ".asia", ".cc", ".tv", ".公司", ".网络", ".中国" };

	private SiteConfigService() {
		siteConfigMap = toMap(siteConfigDao.findAll());
		new AsyncThread(1000 * 3, new Call() {
			@Override
			public void invoke() {
				siteConfigMap = toMap(siteConfigDao.findAll());
			}
		});
	}

	public static SiteConfigService getInstance() {
		return instance;
	}

	public SiteConfig getSiteConfigByRoot(String root) {
		return this.siteConfigMap.get(root);
	}

	private Map<String, SiteConfig> toMap(List<SiteConfig> siteConfigs) {
		Map<String, SiteConfig> ret = new HashMap<String, SiteConfig>();
		for (SiteConfig siteConfig : siteConfigs) {
			ret.put(siteConfig.getRoot(), siteConfig);
		}
		return ret;
	}

	public Map<String, SiteConfig> getSiteConfigMap() {
		return new HashMap<String, SiteConfig>(siteConfigMap);
	}

	public List<SiteConfig> getAllSiteConfig() {
		return new ArrayList<SiteConfig>(this.getSiteConfigMap().values());
	}

	public SiteConfig parseUrl(String url) {
		String s = StringUtils.substringAfter(url, "http://");
		s = StringUtils.substringBefore(s, "/");

		String root = null;
		for (String r : ROOT_DOMAINS) {
			if (s.endsWith(r)) {
				s = StringUtils.substringBeforeLast(s, r);
				s = StringUtils.substringAfterLast(s, ".");
				root = s + r;
				break;
			}
		}
		if (root == null) {
			return null;
		}

		return getSiteConfigByRoot(root);
	}

}
