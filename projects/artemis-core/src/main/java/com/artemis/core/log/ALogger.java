package com.artemis.core.log;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Marker;

import com.artemis.logs.CommonLogger;

/**
 * Logger
 * 
 * @author xiaoyu
 * 
 */
public class ALogger implements CommonLogger {
	private Map<String, org.slf4j.Logger> LOGGER_MAP = new HashMap<String, org.slf4j.Logger>();
	private Class<?> clazz;
	private final String PRINT;
	private final String PERFORMANCE;

	public ALogger(Class<?> clazz) {
		this.clazz = clazz;
		PRINT = "print." + clazz.getName();
		PERFORMANCE = "performance." + clazz.getName();
	}

	private org.slf4j.Logger logger() {
		return logger(clazz.getName());
	}

	private org.slf4j.Logger printLogger() {
		return logger(PRINT);
	}

	private org.slf4j.Logger performanceLogger() {
		return logger(PERFORMANCE);
	}

	private org.slf4j.Logger logger(String name) {
		if (LOGGER_MAP.containsKey(name)) {
			return LOGGER_MAP.get(name);
		}

		org.slf4j.Logger ret = org.slf4j.LoggerFactory.getLogger(name);
		if (ret != null) {
			LOGGER_MAP.put(name, ret);
		}
		return ret;
	}

	@Override
	public void debug(String msg) {
		logger().debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		logger().debug(format, arg);
	}

	@Override
	public void debug(String format, Object[] argArray) {
		logger().debug(format, argArray);
	}

	@Override
	public void debug(Throwable t) {
		logger().debug("", t);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger().debug(msg, t);
	}

	@Override
	public void debug(Marker marker, String msg) {
		logger().debug(marker, msg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		logger().debug(format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		logger().debug(marker, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object[] argArray) {
		logger().debug(marker, format, argArray);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		logger().debug(marker, msg, t);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		logger().debug(marker, format, arg1, arg2);
	}

	@Override
	public void error(String msg) {
		logger().error(msg);
	}

	@Override
	public void error(String format, Object arg) {
		logger().error(format, arg);
	}

	@Override
	public void error(String format, Object[] argArray) {
		logger().error(format, argArray);
	}

	@Override
	public void error(Throwable t) {
		logger().error("", t);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger().error(msg, t);
	}

	@Override
	public void error(Marker marker, String msg) {
		logger().error(marker, msg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		logger().error(format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		logger().error(marker, format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object[] argArray) {
		logger().error(marker, format, argArray);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		logger().error(marker, msg, t);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		logger().error(marker, format, arg1, arg2);
	}

	@Override
	public String getName() {
		return logger().getName();
	}

	@Override
	public void info(String msg) {
		logger().info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		logger().info(format, arg);
	}

	@Override
	public void info(String format, Object[] argArray) {
		logger().info(format, argArray);
	}

	@Override
	public void info(Throwable t) {
		logger().info("", t);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger().info(msg, t);
	}

	@Override
	public void info(Marker marker, String msg) {
		logger().info(marker, msg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		logger().info(format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		logger().info(marker, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object[] argArray) {
		logger().info(marker, format, argArray);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		logger().info(marker, msg, t);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		logger().info(marker, format, arg1, arg2);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger().isDebugEnabled();
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return logger().isDebugEnabled(marker);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger().isErrorEnabled();
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return logger().isErrorEnabled(marker);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger().isInfoEnabled();
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return logger().isInfoEnabled(marker);
	}

	@Override
	public boolean isTraceEnabled() {
		return logger().isTraceEnabled();
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return logger().isTraceEnabled(marker);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger().isWarnEnabled();
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return logger().isWarnEnabled(marker);
	}

	@Override
	public void trace(String msg) {
		logger().trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		logger().trace(format, arg);
	}

	@Override
	public void trace(String format, Object[] argArray) {
		logger().trace(format, argArray);
	}

	@Override
	public void trace(Throwable t) {
		logger().trace("", t);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger().trace(msg, t);
	}

	@Override
	public void trace(Marker marker, String msg) {
		logger().trace(marker, msg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		logger().trace(format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		logger().trace(marker, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object[] argArray) {
		logger().trace(marker, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		logger().trace(marker, msg, t);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		logger().trace(marker, format, arg1, arg2);
	}

	@Override
	public void warn(String msg) {
		logger().warn(msg);
	}

	@Override
	public void warn(String format, Object arg) {
		logger().warn(format, arg);
	}

	@Override
	public void warn(String format, Object[] argArray) {
		logger().warn(format, argArray);
	}

	@Override
	public void warn(Throwable t) {
		logger().warn("", t);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger().warn(msg, t);
	}

	@Override
	public void warn(Marker marker, String msg) {
		logger().warn(marker, msg);
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		logger().warn(format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		logger().warn(marker, format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object[] argArray) {
		logger().warn(marker, format, argArray);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		logger().warn(marker, msg, t);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		logger().warn(marker, format, arg1, arg2);
	}

	@Override
	public void print(String msg) {
		printLogger().info(msg);
	}

	@Override
	public void print(String format, Object arg) {
		printLogger().info(format, arg);
	}

	@Override
	public void print(String format, Object[] argArray) {
		printLogger().info(format, argArray);
	}

	@Override
	public void print(Marker marker, String msg) {
		printLogger().info(marker, msg);
	}

	@Override
	public void print(String format, Object arg1, Object arg2) {
		printLogger().info(format, arg1, arg2);
	}

	@Override
	public void print(Marker marker, String format, Object arg) {
		printLogger().info(marker, format, arg);
	}

	@Override
	public void print(Marker marker, String format, Object[] argArray) {
		printLogger().info(marker, format, argArray);
	}

	@Override
	public void print(Marker marker, String format, Object arg1, Object arg2) {
		printLogger().info(marker, format, arg1, arg2);
	}

	public void performance(long begin, long end) {
		performance(begin, end, null);
	}

	public void performance(long begin, long end, String msg) {
		performance(begin, end, msg, null);
	}

	@Override
	public void performance(long begin, long end, String msg, Object q) {
		long exp = end - begin;
		if (exp < 0) {
			this.logger().error("performance log end time is less than begin time");
		}

		if (exp < 100) {
			return;
		}

		if (performanceLogger().isErrorEnabled() && exp < 500) {
			return;
		}

		if (performanceLogger().isWarnEnabled() && exp < 200) {
			return;
		}

		Object[] array = new Object[3];
		array[0] = exp;
		StringBuilder builder = new StringBuilder();

		if (exp >= 100 && exp < 200) {
			builder.append("[L1-{}ms]");
		} else if (exp >= 200 && exp < 500) {
			builder.append("[L2-{}ms]");
		} else if (exp >= 500) {
			builder.append("[L3-{}ms]");
		}

		if (msg != null) {
			builder.append(" {}");
			array[1] = msg;
		}

		if (q != null) {
			builder.append(" q:").append(q.toString());
		}

		performanceLogger().info(builder.toString(), array);
	}
}
