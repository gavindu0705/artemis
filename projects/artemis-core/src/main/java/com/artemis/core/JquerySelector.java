package com.artemis.core;

/**
 * JQuery选择器功能接口
 * 
 * @author xiaoyu
 * 
 */
public interface JquerySelector extends Resolver {

	/**
	 * JQuery选择符
	 * 
	 * @return
	 */
	public String getSelector();
}
