package com.artemis.plugin.adsl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.core.bean.Proxy;
import com.artemis.core.util.DataUtil;
import com.artemis.core.util.DateUtil;
import com.artemis.mongo.dao.HttpAdslDao;
import com.artemis.mongo.po.HttpAdsl;
import com.mongodb.BasicDBObject;

/**
 * ADSLRestartPlugin
 * 
 * @author xiaoyu
 * 
 */
public class ADSLManagerPlugin {
	private final static Logger LOG = LoggerFactory.getLogger(ADSLManagerPlugin.class);
	private HttpAdslDao httpAdslDao = HttpAdslDao.getInstance();

	private static boolean FLAG = false;

	public static void main(String[] args) {
		ADSLManagerPlugin adslManager = new ADSLManagerPlugin();
		adslManager.run();
	}

	public void run() {
		while (true) {
			try {
				if (!FLAG) {
					updateHttpAdslRestartingToEnable();
					Thread.sleep(3 * 1000);
					FLAG = true;
				}

				HttpAdsl httpAdsl = chooseMostUrgentAdsl();
				if (httpAdsl == null) {
					LOG.info("all of adsl are running well");
					Thread.sleep(10 * 1000);
					continue;
				}

				LOG.info("restarting {}", new Object[] { httpAdsl.getIp() });
				boolean flag = restartAdsl(httpAdsl);
				if (flag) {
					LOG.info("restart {} success", new Object[] { httpAdsl.getIp() });
				} else {
					LOG.info("restart {} error", new Object[] { httpAdsl.getIp() });
				}
				Thread.sleep(3 * 1000);
			} catch (Exception e) {
				LOG.error("", e);
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void updateHttpAdslRestartingToEnable() {
		httpAdslDao.update(new BasicDBObject("status", Proxy.ENABLE), new BasicDBObject("status", Proxy.RESTARTING));
	}

	/**
	 * 选择一个最远离重启频次的ADSL进行重启
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HttpAdsl chooseMostUrgentAdsl() {
		List<HttpAdsl> httpAdslList = httpAdslDao.findHttpAdsl();
		if (httpAdslList == null || httpAdslList.size() == 0) {
			return null;
		}

		ArrayList<HttpAdsl> tmpList = (ArrayList<HttpAdsl>) new ArrayList(httpAdslList).clone();
		List<HttpAdsl> canList = new ArrayList<HttpAdsl>();
		for (HttpAdsl ha : tmpList) {
			if (ha.getRestartFreq() == 0) {
				continue;
			}

			if (ha.getStatus() == Proxy.DISABLE) {
				continue;
			}

			if (ha.getRestartDate() == null) {
				return ha;
			}

			long curr = System.currentTimeMillis();
			if ((curr - ha.getRestartDate().getTime()) > ha.getRestartFreq() * 1000) {
				canList.add(ha);
			}
		}

		if (canList.size() > 0) {
			Collections.sort(canList, new Comparator<HttpAdsl>() {
				@Override
				public int compare(HttpAdsl o1, HttpAdsl o2) {
					if (o1.getRestartDate().before(o2.getRestartDate())) {
						return -1;
					} else if (o1.getRestartDate().getTime() == o2.getRestartDate().getTime()) {
						return 0;
					}
					return 1;
				}
			});
			return canList.get(0);
		}

		return null;
	}

	/**
	 * 重启代理
	 * 
	 * @param httpAdsl
	 * @return
	 * @throws IOException
	 */
	public boolean restartAdsl(HttpAdsl httpAdsl) {
		if (httpAdsl == null) {
			return false;
		}

		boolean ret = false;
		int orgStatus = httpAdsl.getStatus();
		// System.out.println("1### " + orgStatus);
		// 更新为重启中
		httpAdslDao.updateToRestarting(httpAdsl.getId());
		try {
			Thread.sleep(40 * 1000);
		} catch (InterruptedException e1) {
			LOG.info("", e1);
		}
		// System.out.println("2### ");
		String v = DateUtil.formatToYYYYMMDDhhmmss(new Date());
		// System.out.println("3### " + v);
		int errorCount = 0;
		while (true) {
			try {
				HttpAdsl entity = httpAdslDao.findById(httpAdsl.getId());
				if (entity == null || entity.getStatus() == 1) {
					return false;
				}

				// System.out.println("4### ");
				String cmd = "ssh root@" + httpAdsl.getIp() + " /root/auto_adsl.sh " + v;
				DataUtil.runLinuxCommand(cmd);
				Thread.sleep(10 * 1000);
				// System.out.println("5### " + cmd);
				String msg = DataUtil.runLinuxCommand("ssh root@" + httpAdsl.getIp() + " cat /root/iper/re_" + v);
				// System.out.println("6### " + msg);
				if (msg != null && msg.indexOf("curr=") > -1) {
					// System.out.println("7### ");
					String afterIp = StringUtils.substringBetween(msg, "curr=addr:", "]");
					// System.out.println("8### " + afterIp);
					if (afterIp.equals(httpAdsl.getPublicIp())) {
						// System.out.println("9### " + httpAdsl.getPublicIp());
						LOG.info("restart adsl {} ip [{}] the same as before, retry after 10 seconds");
						Thread.sleep(10 * 1000);
						continue;
					} else {
						// System.out.println("10### ");
						httpAdslDao.updateToRestarted(httpAdsl.getId(), orgStatus, afterIp);
						ret = true;
						break;
					}
				} else {
					// System.out.println("11### ");
					if (errorCount > 5) {
						// System.out.println("12### ");
						httpAdslDao.updateToError(httpAdsl.getId());
						ret = false;
						break;
					}
					// System.out.println("13### ");
					Thread.sleep(3 * 1000);
					errorCount++;
				}
			} catch (InterruptedException e) {
				LOG.info("", e);
			}
		}

		return ret;
	}
}
