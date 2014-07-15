package com.artemis.logs;

import org.slf4j.Marker;

public interface CommonLogger extends org.slf4j.Logger {

	/**
	 * 异常输出
	 * 
	 * @param t
	 */
	public void trace(Throwable t);

	public void debug(Throwable t);

	public void info(Throwable t);

	public void warn(Throwable t);

	public void error(Throwable t);

	/**
	 * 类似System.out
	 * 
	 * @param msg
	 */
	public void print(String msg);

	public void print(String format, Object arg);

	public void print(String format, Object[] argArray);

	public void print(Marker marker, String msg);

	public void print(String format, Object arg1, Object arg2);

	public void print(Marker marker, String format, Object arg);

	public void print(Marker marker, String format, Object[] argArray);

	public void print(Marker marker, String format, Object arg1, Object arg2);

	/**
	 * 性能日志
	 * 
	 * @param msg
	 */
	public void performance(long begin, long end);

	public void performance(long begin, long end, String msg);

	public void performance(long begin, long end, String msg, Object q);

}
