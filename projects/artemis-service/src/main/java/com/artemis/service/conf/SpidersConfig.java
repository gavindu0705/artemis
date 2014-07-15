package com.artemis.service.conf;

import java.util.ArrayList;
import java.util.List;

import com.artemis.core.tools.PropertiesLoader;

public abstract class SpidersConfig {
	public static final String PROXY_DOMAIN = "artemis.spiders.proxy";
	public static final String CONFIG_DOMAIN = "artemis.spiders.config";

	public static final String PROP_PATH = "artemis-spiders.properties";

	public PropertiesLoader propertiesLoader;

	public SpidersConfig() {
		propertiesLoader = new PropertiesLoader(PROP_PATH, System.getProperty("spiders.config.path",
				"D:/goojiaconfig/artemis-spiders.properties"));
	}

	public final List<String> getKey(String domain) {
		List<String> ret = new ArrayList<String>();
		for (Object o : propertiesLoader.getKeys()) {
			if (o.toString().startsWith(domain)) {
				ret.add(o.toString());
			}
		}
		return ret;
	}

	public final List<String> getKey(String domain, String name) {
		List<String> ret = new ArrayList<String>();
		String t = domain + "." + name;
		for (Object o : getKey(domain)) {
			if (o.toString().startsWith(t)) {
				ret.add(o.toString());
			}
		}
		return ret;
	}

	public final String getKey(String domain, String name, String sign) {
		String t = domain + "." + name + "." + sign;
		for (String s : getKey(domain, name)) {
			if (s.equals(t)) {
				return s;
			}
		}
		return null;
	}

}
