package com.artemis.spiders.crawl;

import java.math.BigDecimal;

import com.artemis.core.GoalHolder;
import com.artemis.core.HarvestHolder;
import com.artemis.core.log.ALogger;
import com.artemis.core.tools.AsyncThread;
import com.artemis.core.tools.AsyncThread.Call;
import com.artemis.service.proxy.NginxProxyImpl;
import com.artemis.service.proxy.GrandcloudProxyImpl;
import com.artemis.spiders.SpidersConfig;

/**
 * 抓取目标分发控制线程
 * 
 * @author xiaoyu
 * 
 */
public class MasterSpider extends Thread {
	public static final ALogger LOG = new ALogger(MasterSpider.class);
	private final static SpidersConfig CONFIG = SpidersConfig.getInstance();
	private static final int STAT_SKIP_SECS = SpidersConfig.getInstance().getStatLogSkipSecs();

	@Override
	public void run() {
		if (STAT_SKIP_SECS > 0) {
			new AsyncThread(STAT_SKIP_SECS * 1000, new Call() {
				@Override
				public void invoke() {
					double speeds = roundDouble((double) SpidersThread.M_SUC_INC.get() / (double) STAT_SKIP_SECS);
					LOG.print("ALL/SUC/ERR [{}/{}/{} {}/{}/{}] {}/s goal:{} har:{}", new Object[] {
							SpidersThread.TOTAL_INC.get(), SpidersThread.SUC_INC.get(), SpidersThread.ERR_INC.get(),
							SpidersThread.M_TOTAL_INC.get(), SpidersThread.M_SUC_INC.get(), SpidersThread.M_ERR_INC.get(),
							speeds, GoalHolder.getInstance().size(), HarvestHolder.getInstance().size() });
					SpidersThread.M_TOTAL_INC.set(0);
					SpidersThread.M_ERR_INC.set(0);
					SpidersThread.M_SUC_INC.set(0);
				}
			});
		}

		for (int i = 100; i < 100 + CONFIG.getAdslThreadSize(); i++) {
			SpidersThread thread = new SpidersThread(new NginxProxyImpl());
			thread.setName("S-" + i);
			thread.setDaemon(true);
			LOG.print("starting " + thread.getName() + "  ...");
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			thread.start();
		}

		for (int i = 0; i < CONFIG.getGrandCloudThreadSize(); i++) {
			SpidersThread thread = new SpidersThread(new GrandcloudProxyImpl());
			thread.setName("M-" + i);
			thread.setDaemon(true);
			LOG.print("starting " + thread.getName() + "  ...");
			try {
				Thread.sleep(15 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			thread.start();
		}
	}

	private double roundDouble(double d) {
		return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
