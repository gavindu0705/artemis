package com.artemis.service.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.artemis.core.MutiGoalProxyPolicy;
import com.artemis.core.ProxyPolicy;
import com.artemis.core.bean.Goal;
import com.artemis.core.bean.Harvest;
import com.artemis.core.bean.Harvest.HarvestStatusEnum;
import com.artemis.core.bean.HtmlGoal;
import com.artemis.core.bean.Proxy;
import com.artemis.core.log.ALogger;

/**
 * 多目标代理
 * 
 * @author duxiaoyu
 * 
 */
public class GrandcloudProxyImpl implements MutiGoalProxyPolicy {
	private ProxyService proxyService = ProxyService.getInstance();
	public static final ALogger LOG = new ALogger(GrandcloudProxyImpl.class);

	public GrandcloudProxyImpl() {
		proxyService.reload(1 * 1000);
	}

	public static void main(String[] args) throws IOException {
		GrandcloudProxyImpl sd = new GrandcloudProxyImpl();
		HtmlGoal goal1 = new HtmlGoal("http://shanghai.koofang.com/sale/c1/pg1/");
		// HtmlGoal goal2 = new
		// HtmlGoal("http://sou.fangjia.com/zixun/xinwen/50bea95aa9bb9d669cb5a39d");
		// HtmlGoal goal3 = new
		// HtmlGoal("http://sou.fangjia.com/zixun/xinwen/50beaa0ce4b0fbe660cf136f");
		List<Goal> goals = new ArrayList<Goal>();
		goals.add(goal1);
		// goals.add(goal2);
		// goals.add(goal3);
		sd.apply(goals);
	}

	private Proxy selectCloudProxy(Goal goal) {
		while (true) {
			Proxy proxy = proxyService.selectGrandCloudProxy(goal);
			if (proxy != null) {
				return proxy;
			} else {
				try {
					LOG.print("grandcloud is busying now! retry after 1 seconds ...");
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Proxy selectAdslProxy(Goal goal) {
		while (true) {
			Proxy proxy = proxyService.selectAdslCloudProxy(goal);
			if (proxy != null) {
				return proxy;
			} else {
				try {
					// LOG.print("adsl-cloud is busying now! retry after 3 seconds ...");
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<Harvest> apply(List<Goal> goals) throws IOException {
		Goal goal = wrapGoals(goals);
		Map<String, Goal> goalsMap = goalsMap(goals);
		InputStream inputStream = null;
		// String str = DateUtil.formatToTightYYYYMMDDhhmmss(new Date());
		// LOG.print("##" + str + "##");
		Proxy cloudProxy = this.selectCloudProxy(goal);
		Proxy adslProxy = this.selectAdslProxy(goal);

		List<Harvest> ret = new ArrayList<Harvest>();
		try {
			String surl = buildRequestUrl(goal, cloudProxy);
			URL netUrl = new URL(surl);
			HttpURLConnection connection = (HttpURLConnection) netUrl.openConnection(nginxNetProxy(goal, adslProxy));
			connection.setReadTimeout(goals.size() * 10 * 1000);
			connection.setConnectTimeout(goals.size() * 10 * 1000);
			connection.setRequestMethod("POST");
			connection.addRequestProperty("Referer", goal.getReferer());
			connection.addRequestProperty("User-Agent", ProxyPolicy.USER_AGENT_IE);
			// long lstart1 = System.currentTimeMillis();
			int resCode = connection.getResponseCode();

			// long diff1 = System.currentTimeMillis() - lstart1;
			// long diff11 = 0;
			// long diff12 = 0;
			// long diff13 = 0;

			if (resCode == HarvestStatusEnum.SUCCESS.getCode()) {
				inputStream = connection.getInputStream();

				// long lstart11 = System.currentTimeMillis();
				String dataString = IOUtils.toString(inputStream, "UTF-8");
				// diff11 = System.currentTimeMillis() - lstart11;

				// long lstart12 = System.currentTimeMillis();
				JSONObject jo = JSONObject.fromObject(dataString);
				// diff12 = System.currentTimeMillis() - lstart12;

				// long lstart13 = System.currentTimeMillis();
				for (Object u : jo.keySet()) {
					String returl = (String) u;
					JSONObject djo = JSONObject.fromObject(jo.get(u));
					if (djo != null) {
						Harvest harvest = new Harvest();
						harvest.setUrl(returl);
						harvest.setStatusCode(djo.getInt("statusCode"));
						harvest.setContent(djo.getString("data").getBytes(goalsMap.get(returl).getCharset()));
						harvest.setMimeType("text/html");
						harvest.setCaptor(adslProxy.getSimpleName() + "->" + cloudProxy.getSimpleName());
						ret.add(harvest);
					}
				}
				// diff13 = System.currentTimeMillis() - lstart13;
			} else {
				String captor = adslProxy.getSimpleName() + "->" + cloudProxy.getSimpleName();
				for (Goal g : goals) {
					ret.add(new Harvest(g.getUrl(), HarvestStatusEnum.ERROR.getCode(), null, null, captor, g.getReferer()));
				}
			}
			// LOG.print("##" + str + "##" + resCode + " " +
			// nginxProxy.getSimpleName() + "->" + cloudProxy.getSimpleName()
			// + " getcode:" + diff1 + " istos:" + diff11 + " tojo:" + diff12 +
			// " loop:" + diff13 + " all:"
			// + (System.currentTimeMillis() - lstart1));
		} catch (UnsupportedEncodingException e) {
			LOG.print("**error {}->{}", new Object[] { adslProxy.getSimpleName(), cloudProxy.getSimpleName() });
			e.printStackTrace();
		} finally {
			proxyService.endUsingGrandCloud(cloudProxy.getId());
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return ret;
	}

	/**
	 * 选择Nginx
	 * 
	 * @param goal
	 * @return
	 */
	private java.net.Proxy nginxNetProxy(Goal goal, Proxy nginxProxy) {
		String ip = nginxProxy.getUrl().replace("http://", "");
		return new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(ip, nginxProxy.getPort()));
	}

	private Map<String, Goal> goalsMap(List<Goal> goals) {
		Map<String, Goal> ret = new HashMap<String, Goal>();
		for (Goal g : goals) {
			ret.put(g.getUrl(), g);
		}
		return ret;
	}

	private Goal wrapGoals(List<Goal> goals) {
		List<String> urlsGroup = new ArrayList<String>();
		for (Goal g : goals) {
			StringBuilder builder = new StringBuilder();
			builder.append(encodeUrl(g.getUrl()));
			builder.append(",");
			builder.append(g.getCharset());
			builder.append(",");
			builder.append(encodeUrl(g.getReferer()));
			if (g.getTimeout() > 0) {
				builder.append(",");
				builder.append(g.getTimeout());
			}
			urlsGroup.add(builder.toString());
		}

		return new HtmlGoal(StringUtils.join(urlsGroup, ";"));
	}

	public String buildRequestUrl(Goal goal, Proxy proxy) {
		Map<String, Object> params = goal.getAsParams();
		StringBuilder part1 = new StringBuilder();
		StringBuilder part2 = new StringBuilder();

		part1.append(proxy.getUrl());
		if (proxy.getPort() > 0 && proxy.getPort() != 80) {
			part1.append(":").append(proxy.getPort());
		}
		part1.append(proxy.getContextPath());

		if (proxy.getParams() != null) {
			part2.append("?");
			for (String p : proxy.getParams().split(",")) {
				String s = (String) params.get(p);
				if (!"url".equals(p)) {
					s = this.encodeUrl(s);
				}
				part2.append(p).append("=").append(s).append("&");
			}
		}

		String s = part2.toString();
		if (s.endsWith("&") || s.endsWith("?")) {
			s = s.substring(0, s.length() - 1);
		}

		return part1.toString() + s;
	}

	private String encodeUrl(Object s) {
		if (s == null) {
			return "";
		}
		try {
			return URLEncoder.encode(s.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
