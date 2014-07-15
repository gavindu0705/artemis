package com.artemis.service.proxy;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.artemis.core.ProxyPolicy;
import com.artemis.core.SingleGoalProxyPolicy;
import com.artemis.core.bean.Goal;
import com.artemis.core.bean.Harvest;
import com.artemis.core.bean.Harvest.HarvestStatusEnum;
import com.artemis.core.bean.HtmlGoal;
import com.artemis.core.bean.Proxy;
import com.artemis.core.log.ALogger;
import com.artemis.core.util.DataUtil;

/**
 * 利用NGINX ADSL代理服务
 * 
 * @author duxiaoyu
 * 
 */
public class NginxProxyImpl implements SingleGoalProxyPolicy {
	private ProxyService proxyService = ProxyService.getInstance();
	public static final ALogger LOG = new ALogger(NginxProxyImpl.class);

	public NginxProxyImpl() {
		proxyService.reload(1 * 1000);
	}

	public static void main(String[] args) throws IOException {
		String url = "http://cq.fangjia.com/ershoufang-xinxi-53bde0bce4b081fd626ca99d";
		String referer = "http://esf.gy.soufun.com/house/i31/";
		NginxProxyImpl sd = new NginxProxyImpl();
		HtmlGoal goal = new HtmlGoal(url, referer, 5000);
		goal.setCharset(null);
		sd.apply(goal);
	}

	private Proxy selectProxy(Goal goal) {
		while (true) {
			Proxy proxy = proxyService.selectHttpAdslProxy(goal);
			if (proxy != null) {
				return proxy;
			} else {
				try {
					LOG.print("httpAdsl is busying now! retry after 1 seconds ...");
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Harvest apply(Goal goal) {
		Harvest harvest = null;
		Proxy proxy = selectProxy(goal);
		try {
			URL netUrl = new URL(goal.getUrl());
			HttpURLConnection connection = (HttpURLConnection) netUrl.openConnection(nginxNetProxy(goal, proxy));
			connection.setReadTimeout(10 * 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.addRequestProperty("Referer", goal.getReferer());
			connection.addRequestProperty("User-Agent", ProxyPolicy.USER_AGENT_IE);
			connection.addRequestProperty("Content-Encoding", "gzip");
			if (connection.getResponseCode() == HarvestStatusEnum.SUCCESS.getCode()) {
				byte[] data = IOUtils.toByteArray(connection.getInputStream());
				if ("gzip".equals(connection.getHeaderField("Content-Encoding"))) {
					data = DataUtil.unzip(data);
				}
				
				String charset = goal.getCharset();
				if (StringUtils.isBlank(charset)) {
					charset = matchCharset(connection.getHeaderField("Content-Type"));
					if (StringUtils.isBlank(charset)) {
						charset = matchCharset(new String(data));
					}
				}

				String content = null;
				if (StringUtils.isBlank(charset)) {
					content = new String(data);
				} else {
					content = new String(data, charset);
				}

				if (content == null || content.indexOf("你的访问过于频繁") > -1 || content.indexOf("您的访问速度太快了") > -1) {
					harvest = new Harvest(goal.getUrl(), HarvestStatusEnum.ERROR.getCode(), proxy.getSimpleName());
				} else {
					harvest = new Harvest(goal.getUrl(), connection.getResponseCode(), connection.getContentType(), data,
							proxy.getSimpleName(), goal.getReferer());
				}
			} else {
				harvest = new Harvest(goal.getUrl(), connection.getResponseCode(), proxy.getSimpleName());
			}
		} catch (IOException e) {
			harvest = new Harvest(goal.getUrl(), HarvestStatusEnum.ERROR.getCode(), proxy.getSimpleName());
		}
		return harvest;
	}

	public static final Pattern pattern = Pattern.compile("charset=\"?([\\w\\d-]+)\"?;?", Pattern.CASE_INSENSITIVE);

	private String matchCharset(String input) {
		if (input == null) {
			return null;
		}

		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	private java.net.Proxy nginxNetProxy(Goal goal, Proxy proxy) {
		String ip = proxy.getUrl().replace("http://", "");
		return new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(ip, proxy.getPort()));
	}

}
