package com.artemis.spiders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.artemis.core.tools.PropertiesKey;

public class SpidersConfig extends com.artemis.service.conf.SpidersConfig {
	private static SpidersConfig instance = new SpidersConfig();

	private SpidersConfig() {
		super();
	}

	public static SpidersConfig getInstance() {
		return instance;
	}

	public Map<PropertiesKey, String> getProxyClass() {
		Map<PropertiesKey, String> ret = new HashMap<PropertiesKey, String>();
		for (PropertiesKey o : propertiesLoader.getKeys()) {
			if (o.getDomain().equals(PROXY_DOMAIN) && o.getSign().equals("class")) {
				ret.put(o, propertiesLoader.getStringValue(o.getKey()));
			}
		}
		return ret;
	}

	public int getMutiGrandCloudProxyBatchSize() {
		return propertiesLoader.getIntegerValue(getKey(PROXY_DOMAIN, "grandcloud", "batchSize"));
	}

	public int getGrandCloudThreadSize() {
		return propertiesLoader.getIntegerValue(getKey(PROXY_DOMAIN, "grandcloud", "size"));
	}

	public int getAdslThreadSize() {
		return propertiesLoader.getIntegerValue(getKey(PROXY_DOMAIN, "adsl", "size"));
	}

	public boolean isEnableDetailLog() {
		return "true".equals(propertiesLoader.getStringValue(getKey(CONFIG_DOMAIN, "detailLog", "print")));
	}

	public int getStatLogSkipSize() {
		return propertiesLoader.getIntegerValue(getKey(CONFIG_DOMAIN, "statLog", "skipSize"));
	}
	
	public int getStatLogSkipSecs() {
		return propertiesLoader.getIntegerValue(getKey(CONFIG_DOMAIN, "statLog", "skipSecs"));
	}

	public Map<String, String> getPropertiesAsMap() {
		Set<PropertiesKey> keys = propertiesLoader.getKeys();
		Map<String, String> ret = new HashMap<String, String>();
		for(PropertiesKey k : keys) {
			ret.put(k.getKey(), propertiesLoader.getStringValue(k.getKey()));
		}
		return ret;
	}

}
