package com.artemis.core.tools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public abstract class AsyncThreadQueue {
	private final Thread asyncThread;
	private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10000000);

	public AsyncThreadQueue() {
		asyncThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Runnable runnable = queue.take();
						if (runnable != null) {
							runnable.run();
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		}, "AsyncThread");
		asyncThread.setDaemon(true);
		asyncThread.start();
	}

	public void run(Runnable runnable) {
		try {
			queue.put(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
