package com.artemis.service.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.math.RandomUtils;

import com.artemis.core.bean.Goal;
import com.artemis.core.bean.Proxy;
import com.artemis.core.log.ALogger;
import com.artemis.core.tools.AsyncThread;
import com.artemis.core.tools.AsyncThread.Call;
import com.artemis.mongo.dao.GrandCloudDao;
import com.artemis.mongo.dao.HttpAdslDao;
import com.artemis.mongo.po.GrandCloud;
import com.artemis.mongo.po.HttpAdsl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 代理控制类
 * 
 * @author duxiaoyu
 * 
 */
public class ProxyService {
	private static ProxyService instance = new ProxyService();
	private static boolean RELOADED = false;
	private HttpAdslDao httpAdslDao = HttpAdslDao.getInstance();
	private GrandCloudDao grandCloudDao = GrandCloudDao.getInstance();
	private List<HttpAdsl> httpAdslList = null;
	private List<GrandCloud> grandCloudList = null;

	private static AtomicLong INCREATOR = new AtomicLong(0);
	public static final ALogger LOG = new ALogger(ProxyService.class);

	public static ProxyService getInstance() {
		return instance;
	}

	private ProxyService() {
		grandCloudDao.updateAllUsingToEnabel();
		httpAdslList = httpAdslDao.findHttpAdsl();
		grandCloudList = grandCloudDao.findGrandCloud();
	}

	public void reload(int intervalMills) {
		if (!RELOADED) {
			RELOADED = true;
			reloadProxy(intervalMills);
		}
	}

	private void reloadProxy(int intervalMills) {
		new AsyncThread(intervalMills, new Call() {
			@Override
			public void invoke() {
				httpAdslList = httpAdslDao.findHttpAdsl();
				grandCloudList = grandCloudDao.findGrandCloud();
			}
		});
	}

	
	public Proxy selectAdslCloudProxy(Goal goal) {
		HttpAdsl ret = null;
		List<HttpAdsl> targetList = new ArrayList<HttpAdsl>();
		for (HttpAdsl ha : httpAdslList) {
			if (ha.getStatus() == Proxy.DISABLE || ha.getStatus() == Proxy.ERROR || ha.getStatus() == Proxy.RESTARTING) {
				continue;
			}

			// 不允许访问云
			if (ha.getCloudVisite() == 0) {
				continue;
			}

			if (ha.getCloudVisitedDate() == null || ha.getCloudFreq() <= 0) {
				targetList.add(ha);
			} else {
				long diff2 = System.currentTimeMillis() - ha.getCloudVisitedDate().getTime();
				if (diff2 > ha.getCloudFreq() * 1000) {
					targetList.add(ha);
				}
			}
		}

		if (targetList.size() > 0) {
			ret = targetList.get(randomInt(targetList.size()));
		}

		if (ret != null) {
			this.httpAdslDao.updateById(new BasicDBObject("cv_date", new Date()), ret.getId());
		}

		return ret;
	}
	
	/**
	 * 盛大云代理
	 * 
	 * @param goal
	 * @return
	 */
	public Proxy selectGrandCloudProxy(Goal goal) {
		GrandCloud ret = null;
		List<GrandCloud> targetList = new ArrayList<GrandCloud>();
		for (GrandCloud cloud : grandCloudList) {
			if (cloud.getStatus() == Proxy.DISABLE || cloud.getStatus() == Proxy.ERROR || cloud.getStatus() == Proxy.RESTARTING) {
				continue;
			}

			if (cloud.getVisitedDate() == null) {
				targetList.add(cloud);
				continue;
			}

			long timeRange = System.currentTimeMillis() - cloud.getVisitedDate().getTime();
			if (cloud.getMutis() == 1) {
				// 支持多线程
				if (timeRange > waveFreq(cloud.getFreq()) * 1000) {
					targetList.add(cloud);
				}
			} else {
				// 不支持多线程
				if (cloud.getStatus() == Proxy.ENABLE && timeRange > waveFreq(cloud.getFreq()) * 1000) {
					targetList.add(cloud);
				}
			}
		}

		if (targetList.size() > 0) {
			ret = targetList.get(randomInt(targetList.size()));
		}

		if (ret != null) {
			DBObject data = new BasicDBObject();
			data.put("v_date", new Date());
			data.put("status", Proxy.USING);
			this.grandCloudDao.updateById(data, ret.getId());
		}

		return ret;
	}

	/**
	 * 结束使用云代理
	 * 
	 * @param id
	 */
	public void endUsingGrandCloud(String id) {
		DBObject data = new BasicDBObject();
		data.put("status", Proxy.ENABLE);
		this.grandCloudDao.updateById(data, id);
	}

	/**
	 * Http Adsl代理
	 * 
	 * @param goal
	 * @return
	 */
	public Proxy selectHttpAdslProxy(Goal goal) {
		HttpAdsl ret = null;
		List<HttpAdsl> targetList = new ArrayList<HttpAdsl>();
		for (HttpAdsl ha : httpAdslList) {
			if (ha.getStatus() == Proxy.DISABLE || ha.getStatus() == Proxy.ERROR || ha.getStatus() == Proxy.RESTARTING) {
				continue;
			}

			if (ha.getVisitedDate() == null || ha.getFreq() <= 0) {
				targetList.add(ha);
			} else {
				long diff2 = new Date().getTime() - ha.getVisitedDate().getTime();
				if (diff2 > ha.getFreq() * 1000) {
					targetList.add(ha);
				}
			}
		}

		if (targetList.size() > 0) {
			ret = targetList.get(randomInt(targetList.size()));
		}

		if (ret != null) {
			this.httpAdslDao.updateById(new BasicDBObject("v_date", new Date()), ret.getId());
		}

		return ret;
	}

	/**
	 * 随机波动的频次
	 * 
	 * @param freq
	 * @return
	 */
	private int waveFreq(int freq) {
		if (RandomUtils.nextBoolean()) {
			freq += RandomUtils.nextInt(10);
		} else {
			freq -= RandomUtils.nextInt(10);
		}
		return freq;
	}

	private int randomInt(int maxValue) {
		return RandomUtils.nextInt(new Random(INCREATOR.incrementAndGet() + new Date().getTime()), maxValue);
	}

	public List<GrandCloud> getGrandCloudList() {
		return grandCloudList;
	}

	public List<HttpAdsl> getHttpAdslList() {
		return httpAdslList;
	}

}
