package com.artemis.bkoff;

import java.io.IOException;
import java.net.URISyntaxException;

public class BkoffConfig extends BaseConfiguration {
	private static BkoffConfig CONFIG;
	private final String baseDomain;

	public BkoffConfig() throws IOException, URISyntaxException {
		super(System.getProperty("fangjia.overridePropertiesLocation", "D:/goojiaconfig/artemis-bkoff.ini"));
		baseDomain = this.get("artemis.bkoff.baseDomain", "artemis.com");
	}

	public static BkoffConfig getConfig() {
		if (null == CONFIG) {
			synchronized (BkoffConfig.class) {
				if (null == CONFIG) {
					try {
						BkoffConfig config = new BkoffConfig();
						CONFIG = config;
					} catch (IOException e) {
						LOGGER.error("error init config: ", e);
					} catch (URISyntaxException e) {
						LOGGER.error("error init config: ", e);
					}
				}
			}
		}
		return CONFIG;
	}

	public String getBaseDomain() {
		return baseDomain;
	}
}
