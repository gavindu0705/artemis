package com.artemis.service.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.artemis.core.log.ALogger;
import com.artemis.core.tools.BloomFilter;

/**
 * 查看URL防止重复抓取
 * 
 * @author xiaoyu
 * 
 */
public class Footprint {
	private static final String BIT_SET_FILE_NAME = "footprint.bit.set";
	private static Footprint instance = null;
	private static List<BloomFilter> BLOOM_FILTERS = new ArrayList<BloomFilter>();
	private static boolean IS_INIT = false;

	public static final ALogger LOG = new ALogger(Footprint.class);

	private Footprint() {

	}

	public static Footprint getInstance() {
		if (!IS_INIT) {
			LOG.error("", new Exception("init this object must before getInstance"));
			return null;
		}

		if (instance == null) {
			instance = new Footprint();
		}
		return instance;
	}

	public static void init(String bloomPath) {
		if (!IS_INIT) {
			BLOOM_FILTERS.add(new BloomFilter(bloomPath + "/" + BIT_SET_FILE_NAME + "_" + 0));
			BLOOM_FILTERS.add(new BloomFilter(bloomPath + "/" + BIT_SET_FILE_NAME + "_" + 1));
			BLOOM_FILTERS.add(new BloomFilter(bloomPath + "/" + BIT_SET_FILE_NAME + "_" + 2));
			IS_INIT = true;
		} else {
			LOG.info("it has been initialized, This operation is ignored");
		}
	}

	/**
	 * 是否在该任务中已经存在或处理过
	 * 
	 * @param url
	 * @param jobId
	 * @return
	 */
	public boolean exists(String url, String jobId, String sessionId) {
		if (url == null || jobId == null) {
			return false;
		}
		String s0 = idGenerator0(url, jobId, sessionId);
		String s1 = idGenerator1(url, jobId, sessionId);
		String s2 = idGenerator2(url, jobId, sessionId);
		if (BLOOM_FILTERS.get(0).exist(s0) && BLOOM_FILTERS.get(1).exist(s1) && BLOOM_FILTERS.get(2).exist(s2)) {
			return true;
		}
		return false;
	}

	public void print(String url, String jobId, String sessionId) {
		BLOOM_FILTERS.get(0).add(idGenerator0(url, jobId, sessionId));
		BLOOM_FILTERS.get(1).add(idGenerator1(url, jobId, sessionId));
		BLOOM_FILTERS.get(2).add(idGenerator2(url, jobId, sessionId));
	}

	private String idGenerator0(String url, String jobId, String sessionId) {
		return url + "#" + jobId + "#" + sessionId;
	}

	private String idGenerator1(String url, String jobId, String sessionId) {
		String urlm = DigestUtils.md5Hex(url + "#" + url.hashCode());
		String jobIdm = DigestUtils.md5Hex(jobId + "#" + jobId.hashCode());
		String sessionIdm = DigestUtils.md5Hex(sessionId + "#" + sessionId.hashCode());
		return urlm + jobIdm + sessionIdm;
	}

	private String idGenerator2(String url, String jobId, String sessionId) {
		byte[] urlm = (url + "|" + url.hashCode()).getBytes();
		byte[] jobIdm = (jobId + "|" + jobId.hashCode()).getBytes();
		byte[] sessionIdm = (sessionId + "|" + sessionId.hashCode()).getBytes();
		return DigestUtils.md5Hex(urlm) + DigestUtils.md5Hex(jobIdm) + DigestUtils.md5Hex(sessionIdm);
	}

	public static void main(String[] args) throws Exception {
		String url = "www.fangjia.com";
		String jobId = "111111";
		String sessionId = "222222";
		Footprint.init("D:/goojiaconfig");

		Footprint.getInstance().print(url, jobId, sessionId);
		System.out.println("");
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId));
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId + "1111"));
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId + "2323"));
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId));

		Thread.sleep(3000 * 1000);
		System.out.println("----------------------");

		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId));
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId + "1111"));
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId + "2323"));
		System.out.println(Footprint.getInstance().exists(url, jobId, sessionId));
	}
}
