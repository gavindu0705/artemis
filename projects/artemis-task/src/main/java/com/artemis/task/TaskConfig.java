package com.artemis.task;

import java.util.ArrayList;
import java.util.List;

import com.artemis.core.tools.PropertiesLoader;

public class TaskConfig {

	private static TaskConfig instance = new TaskConfig();
	public static final String PROP_PATH = "artemis-task.properties";
	private PropertiesLoader propertiesLoader;

	private TaskConfig() {
		propertiesLoader = new PropertiesLoader(PROP_PATH, System.getProperty("spiders.config.path",
				"D:/goojiaconfig/artemis-task.properties"));
	}

	public static TaskConfig getInstance() {
		return instance;
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

	/**
	 * 任务处理线程数量
	 * 
	 * @return
	 */
	public int getTaskerThreadSize() {
		return propertiesLoader.getIntegerValue(this.getKey("artemis.task", "thread", "size"));
	}

	/**
	 * bloom filter文件存储地址
	 * 
	 * @return
	 */
	public String getBloomFilePath() {
		return propertiesLoader.getStringValue(this.getKey("artemis.task", "bloom", "path"));
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

	public static void main(String[] args) {
		int a = TaskConfig.getInstance().getTaskerThreadSize();
		System.out.println(a);
	}

}
