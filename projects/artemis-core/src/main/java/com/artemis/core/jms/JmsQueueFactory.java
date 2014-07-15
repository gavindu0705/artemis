package com.artemis.core.jms;

import org.apache.activemq.command.ActiveMQQueue;

/**
 * JMS队列工厂
 * 
 * @author xiaoyu
 * 
 */
public class JmsQueueFactory {
	private static JmsQueueFactory instance = new JmsQueueFactory();
	private static ActiveMQQueue TASK_QUEUE = new ActiveMQQueue("task.init.queue");

	private JmsQueueFactory() {

	}

	public static JmsQueueFactory getInstance() {
		return instance;
	}

	public ActiveMQQueue getTaskQueue() {
		return TASK_QUEUE;
	}
}
