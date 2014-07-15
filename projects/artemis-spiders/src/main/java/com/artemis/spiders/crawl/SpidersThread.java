package com.artemis.spiders.crawl;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import com.artemis.core.GoalHolder;
import com.artemis.core.HarvestHolder;
import com.artemis.core.ProxyPolicy;
import com.artemis.core.SingleGoalProxyPolicy;
import com.artemis.core.bean.Harvest;
import com.artemis.core.bean.Harvest.HarvestStatusEnum;
import com.artemis.core.bean.HtmlGoal;
import com.artemis.core.log.ALogger;
import com.artemis.spiders.SpidersConfig;

/**
 * 蜘蛛执行线程
 * 
 * @author duxiaoyu
 * 
 */
public class SpidersThread extends Thread {
	private SingleGoalProxyPolicy proxyPolicy;
	private GoalHolder goalsHolder = GoalHolder.getInstance();
	private HarvestHolder harvestHolder = HarvestHolder.getInstance();

	// private static final long START_MILLS = System.currentTimeMillis() - 1;

	public static final AtomicLong TOTAL_INC = new AtomicLong(0);
	public static final AtomicLong SUC_INC = new AtomicLong(0);
	public static final AtomicLong ERR_INC = new AtomicLong(0);

	public static final AtomicLong M_TOTAL_INC = new AtomicLong(0);
	public static final AtomicLong M_SUC_INC = new AtomicLong(0);
	public static final AtomicLong M_ERR_INC = new AtomicLong(0);

	private static final ALogger LOG = new ALogger(SpidersThread.class);
	// private static final int STAT_SKIP_SIZE =
	// SpidersConfig.getInstance().getStatLogSkipSize();
	private static final boolean IS_ENABLE_DETAILLOG = SpidersConfig.getInstance().isEnableDetailLog();

	public SpidersThread(ProxyPolicy proxyPolicy) {
		this.proxyPolicy = (SingleGoalProxyPolicy) proxyPolicy;
	}

	@Override
	public void run() {
		while (true) {
			HtmlGoal goal = goalsHolder.get();
			if (goal == null) {
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			long lstart = System.currentTimeMillis();
			Harvest har = null;
			try {
				har = proxyPolicy.apply(goal);
			} catch (IOException e) {
				LOG.error("single goal proxy error ", e);
			} finally {

			}

			long secs = System.currentTimeMillis() - lstart;
			if (har != null) {
				if (har.getStatusCode() == HarvestStatusEnum.SUCCESS.getCode()) {
					SUC_INC.incrementAndGet();
					M_SUC_INC.incrementAndGet();
				} else if (har.getStatusCode() == HarvestStatusEnum.ERROR.getCode()) {
					ERR_INC.incrementAndGet();
					M_ERR_INC.incrementAndGet();
				} else {
					ERR_INC.incrementAndGet();
					M_ERR_INC.incrementAndGet();
				}
				// 放入成果收集器
				harvestHolder.put(har);

				if (IS_ENABLE_DETAILLOG || har.getStatusCode() != HarvestStatusEnum.SUCCESS.getCode()) {
					LOG.print("[{}] {} {}ms {}", new Object[] { har.getStatusCode(), har.getCaptor(), secs, har.getUrl() });
				}
			} else {
				ERR_INC.incrementAndGet();
				M_ERR_INC.incrementAndGet();
				harvestHolder.put(new Harvest(goal.getUrl(), HarvestStatusEnum.ERROR.getCode()));
			}

			M_TOTAL_INC.incrementAndGet();
			TOTAL_INC.incrementAndGet();
			// if (TOTAL_INC.get() % STAT_SKIP_SIZE == 0) {
			// long currMills = System.currentTimeMillis();
			// double sp = roundDouble((double) SUC_INC.get() / (double)
			// ((currMills - START_MILLS) / 1000));
			// LOG.print("{}/{}(T/F) {}/s {} goal:{} har:{}", new Object[] {
			// SUC_INC.get(), ERR_INC.get(), sp, TOTAL_INC.get(),
			// goalsHolder.size(), harvestHolder.size() });
			// }
		}
	}
}
