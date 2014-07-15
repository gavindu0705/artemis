package com.artemis.core.bean;

import java.util.Date;

public interface Proxy {

	public static final int ENABLE = 0; // 正常

	public static final int DISABLE = 1;// 禁用

	public static final int RESTARTING = 2;// 重启中
	
	public static final int ERROR = 3;// 错误
	
	public static final int USING = 4;// 使用中
	
	public String getId();

	public String getUrl();

	public int getPort();

	public String getContextPath();

	public String getParams();

	public int getFreq();

	public Date getVisitedDate();

	public String getSimpleName();
}