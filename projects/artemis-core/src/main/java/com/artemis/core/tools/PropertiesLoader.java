package com.artemis.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class PropertiesLoader {
	private Properties properties;
	private Properties overrideProperties;

	public PropertiesLoader(String propertiesPath) {
		properties = loader(propertiesPath);
	}

	public PropertiesLoader(String propertiesPath, String overridePath) {
		properties = loader(propertiesPath);
		overrideProperties = loader(overridePath);
	}

	private Properties loader(String path) {
		Properties ret = null;
		try {
			ret = new Properties();
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
			if (is == null) {
				is = new FileInputStream(new File(path));
			}
			ret.load(is);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public Set<PropertiesKey> getKeys() {
		Set<PropertiesKey> ret = new HashSet<PropertiesKey>();
		for (Object k : properties.keySet()) {
			try {
				ret.add(splitKey((String) k));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Object k : overrideProperties.keySet()) {
			try {
				ret.add(splitKey((String) k));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public Integer getIntegerValue(String key) {
		return Integer.parseInt((String) get(key));
	}

	public String getStringValue(String key) {
		return (String) get(key);
	}

	private PropertiesKey splitKey(String key) throws Exception {
		String[] arrs = StringUtils.split(key, ".");
		if (arrs.length < 3) {
			throw new Exception("properties key error");
		}
		String sign = arrs[arrs.length - 1];
		String name = arrs[arrs.length - 2];
		String domain = StringUtils.substringBeforeLast(key, "." + name + "." + sign);
		return new PropertiesKey(domain, name, sign);
	}

	private Object get(String key) {
		Object obj = null;
		if (overrideProperties != null) {
			obj = overrideProperties.getProperty(key);
		}

		if (obj == null) {
			obj = properties.getProperty(key);
		}
		return obj;
	}

	public static void main(String[] args) {
		
	}
}
