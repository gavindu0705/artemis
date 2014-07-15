package com.artemis.core.bean;

import java.util.Map;

/**
 * Ä¿±ê
 * 
 * @author xiaoyu
 * 
 */
public interface Goal {

	public String getCharset();

	public int getTimeout();

	public String getUrl();

	public String getReferer();

	public Map<String, Object> getAsParams();
}
