package com.test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.artemis.core.bean.HtmlGoal;

public class Test {
	private BlockingQueue<HtmlGoal> goalsQueue = new LinkedBlockingQueue<HtmlGoal>(1000);
	Map<String, BlockingQueue<HtmlGoal>> queueMap = new LinkedHashMap<String, BlockingQueue<HtmlGoal>>();

	public Test() {
		queueMap.put("anjuke", new LinkedBlockingQueue<HtmlGoal>(1000));
		queueMap.put("soufun", new LinkedBlockingQueue<HtmlGoal>(1000));
	}

	public static void main(String[] args) throws InterruptedException {
		Test t = new Test();
		t.test();
	}

	public void test() throws InterruptedException {
		for (int i = 1; i <= 100000; i++) {
			if (i % 3 == 0) {
				BlockingQueue<HtmlGoal> tmp = queueMap.get("anjuke");
				tmp.add(new HtmlGoal(i + ""));
				queueMap.put("anjuke", tmp);
			} else {
				BlockingQueue<HtmlGoal> tmp = queueMap.get("soufun");
				tmp.put(new HtmlGoal(i + ""));
				queueMap.put("anjuke", tmp);
			}
//			goalsQueue.put(new HtmlGoal(i + ""));
		}

		System.out.println("------anjuke----------" + queueMap.get("anjuke").size());
		System.out.println("------soufun----------" + queueMap.get("soufun").size());
		System.out.println("----------------------" + goalsQueue.size());
	}

}
