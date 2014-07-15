package com.artemis.core;

import java.util.Map;

import com.artemis.core.result.Result;

/**
 * 功能方法接口定义
 * 
 * @author xiaoyu
 * 
 */
public interface Resolver {
	public static final String URL = "$url";
	public static final String LINKS = "$links";
	public static final String CONENT = "$content";
	public static final String REFERER = "$referer";
	public static final String SELECTOR = "$selector";
	public static final String PARAM_NAME = "$name";
	public static final String ATTR_NAME = "$attr";

	/**
	 * 处理方法
	 * 
	 * @return
	 */
	public Result invoke(Map<String, Object> params);

	// /**
	// * 参数名称
	// *
	// * @return
	// */
	// public String getName();
	//
	// /**
	// * 网页内容
	// *
	// * @return
	// */
	// public String getContent();
}
