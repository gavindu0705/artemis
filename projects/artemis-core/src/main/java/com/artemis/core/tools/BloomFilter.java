package com.artemis.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;

import com.artemis.core.log.ALogger;
import com.artemis.core.tools.AsyncThread.Call;

/**
 * 布隆过滤器
 * 
 * @author xiaoyu
 * 
 */
public class BloomFilter {
	public static final ALogger LOG = new ALogger(BloomFilter.class);
	private final int BIT_SET_DEFAULT_SIZE = Integer.MAX_VALUE;
	private int BASIC = BIT_SET_DEFAULT_SIZE - 1;

	private String bitFilePath;
	private BitSet bits = new BitSet(BIT_SET_DEFAULT_SIZE);

	public BloomFilter(final String bitFilePath) {
		this.bitFilePath = bitFilePath;

		try {
			readBit(bitFilePath);
		} catch (IOException e) {
			LOG.error("read error ", e);
		}

		// 每隔一分钟保存
		new AsyncThread(30 * 1000, new Call() {
			public void invoke() {
				long lstart = System.currentTimeMillis();
				saveBit();
				LOG.performance(lstart, System.currentTimeMillis(), "rewrite bit set to " + bitFilePath);
			}
		});
	}

	private int[] lrandom(String key) {
		int[] randomsum = new int[8];
		int random1 = hashCode(key, 1);
		int random2 = hashCode(key, 2);
		int random3 = hashCode(key, 3);
		int random4 = hashCode(key, 4);
		int random5 = hashCode(key, 5);
		int random6 = hashCode(key, 6);
		int random7 = hashCode(key, 7);
		int random8 = hashCode(key, 8);
		randomsum[0] = random1;
		randomsum[1] = random2;
		randomsum[2] = random3;
		randomsum[3] = random4;
		randomsum[4] = random5;
		randomsum[5] = random6;
		randomsum[6] = random7;
		randomsum[7] = random8;
		return randomsum;
	}

	public void add(String key) {
		if (exist(key)) {
			return;
		}
		int keyCode[] = lrandom(key);
		bits.set(keyCode[0]);
		bits.set(keyCode[1]);
		bits.set(keyCode[2]);
		bits.set(keyCode[3]);
		bits.set(keyCode[4]);
		bits.set(keyCode[5]);
		bits.set(keyCode[6]);
		bits.set(keyCode[7]);
	}

	public boolean exist(String key) {
		int keyCode[] = lrandom(key);
		if (bits.get(keyCode[0]) && bits.get(keyCode[1]) && bits.get(keyCode[2]) && bits.get(keyCode[3]) && bits.get(keyCode[4])
				&& bits.get(keyCode[5]) && bits.get(keyCode[6]) && bits.get(keyCode[7])) {
			return true;
		}
		return false;
	}

	private int hashCode(String key, int Q) {
		int h = 0;
		int off = 0;
		char val[] = key.toCharArray();
		int len = key.length();
		for (int i = 0; i < len; i++) {
			h = (30 + Q) * h + val[off++];
		}
		return changeInteger(h);
	}

	private int changeInteger(int h) {
		return BASIC & h;
	}

	private void saveBit() {
		try {
			File file = new File(this.bitFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, false));
			oos.writeObject(bits);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			LOG.error("save bit error", e);
		} catch (IOException e) {
			LOG.error("save bit error", e);
		}
	}

	private void readBit(String bitFilePath) throws IOException {
		BitSet bits = new BitSet(BIT_SET_DEFAULT_SIZE);
		File file = new File(bitFilePath);
		if (!file.exists()) {
			return;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			bits = (BitSet) ois.readObject();
			ois.close();

			// 合并文件中已有的记录和新记录
			this.bits.or(bits);
		} catch (IOException e) {
			LOG.error("read bit error", e);
			throw e;
		} catch (ClassNotFoundException e) {
			LOG.error("read bit error", e);
		}
	}
}
