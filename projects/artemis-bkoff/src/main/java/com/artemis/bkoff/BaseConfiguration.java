package com.artemis.bkoff;

import java.io.IOException;
import java.net.URISyntaxException;

import org.sothis.core.config.ConfigurationSupport;

public class BaseConfiguration extends ConfigurationSupport {

	protected final String environment;
	protected final boolean disableCache;
	protected final boolean disableLocalCache;

	public BaseConfiguration(String overridePropertiesLocation)
			throws IOException, URISyntaxException {
		super(overridePropertiesLocation);
		this.environment = get("fangjia.environment", "development");
		disableCache = getBoolean("fangjia.disableCache", true);
		disableLocalCache = getBoolean("fangjia.disableLocalCache", true);
	}

	public final String getEnvironment() {
		return environment;
	}

	/**
	 * 是否在开发环境中运行
	 * 
	 * @return
	 */
	public final boolean isDevelopmentEnvironment() {
		return "development".equalsIgnoreCase(getEnvironment());
	}

	/**
	 * 是否在测试环境中运行
	 * 
	 * @return
	 */
	public final boolean isTestEnvironment() {
		return "test".equalsIgnoreCase(getEnvironment());
	}

	/**
	 * 是否在生产环境中运行
	 * 
	 * @return
	 */
	public final boolean isProductionEnvironment() {
		return "production".equalsIgnoreCase(getEnvironment());
	}

	/**
	 * 判断是否禁用cache，默认为true
	 * 
	 * @return
	 */
	public final boolean isDisableCache() {
		return disableCache;
	}

	/**
	 * 判断是否禁用本地缓存cache，默认为true
	 * 
	 * @return
	 */
	public final boolean isDisableLocalCache() {
		return disableLocalCache;
	}

}
