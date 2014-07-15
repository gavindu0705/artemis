package com.artemis.logs;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.SystemUtils;
import org.springframework.util.Log4jConfigurer;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.Log4jWebConfigurer;
import org.springframework.web.util.WebUtils;

/**
 * Log4jConfigListener
 * 
 * @author xiaoyu
 * 
 */
public class Log4jConfigListener implements ServletContextListener {
	public static final String REFRESH_INTERVAL_PARAM = "log4jRefreshInterval";
	public static final String EXPOSE_WEB_APP_ROOT_PARAM = "log4jExposeWebAppRoot";

	public void contextInitialized(ServletContextEvent event) {
		if (exposeWebAppRoot(event.getServletContext())) {
			WebUtils.setWebAppRootSystemProperty(event.getServletContext());
		}

		String location;
		if (SystemUtils.IS_OS_WINDOWS) {
			location = "classpath:log4j_windows.xml";
		} else {
			location = "classpath:log4j_linux.xml";
		}

		try {
			if (!ResourceUtils.isUrl(location)) {
				location = SystemPropertyUtils.resolvePlaceholders(location);
				location = WebUtils.getRealPath(event.getServletContext(), location);
			}

			event.getServletContext().log("Initializing log4j from [" + location + "]");
			String intervalString = event.getServletContext().getInitParameter(REFRESH_INTERVAL_PARAM);
			if (intervalString != null) {
				try {
					long refreshInterval = Long.parseLong(intervalString);
					Log4jConfigurer.initLogging(location, refreshInterval);
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException("Invalid 'log4jRefreshInterval' parameter: " + ex.getMessage());
				}
			} else {
				Log4jConfigurer.initLogging(location);
			}
		} catch (FileNotFoundException ex) {
			throw new IllegalArgumentException("Invalid 'log4jConfigLocation' parameter: " + ex.getMessage());
		}
	}

	private static boolean exposeWebAppRoot(ServletContext servletContext) {
		String exposeWebAppRootParam = servletContext.getInitParameter(EXPOSE_WEB_APP_ROOT_PARAM);
		return (exposeWebAppRootParam == null || Boolean.valueOf(exposeWebAppRootParam));
	}

	public void contextDestroyed(ServletContextEvent event) {
		Log4jWebConfigurer.shutdownLogging(event.getServletContext());
	}
}
