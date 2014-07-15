package com.artemis.core.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * JmsÄ£°å¹¤³§
 * 
 * @author xiaoyu
 * 
 */
public class JmsTemplateFactory {
	private static JmsTemplateFactory instance = new JmsTemplateFactory();
	private static Map<String, JmsTemplate> JMS_TEMPLATE_HOLDER = new HashMap<String, JmsTemplate>();

	private JmsTemplateFactory() {

	}

	public static JmsTemplateFactory getInstance() {
		return instance;
	}

	// fangjia.jms.host=jms.db.fangjia.com
	// fangjia.jms.port=61616
	// tcp://${fangjia.jms.host}:${fangjia.jms.port}
	public JmsTemplate createTemplate(String host, int port) {
		String brokerUrl = "tcp://" + host + ":" + port;
		if (JMS_TEMPLATE_HOLDER.containsKey(brokerUrl)) {
			return JMS_TEMPLATE_HOLDER.get(brokerUrl);
		}

		PooledConnectionFactory pooledFactory = new PooledConnectionFactory();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		pooledFactory.setConnectionFactory(connectionFactory);
		JmsTemplate jmsTempldate = new JmsTemplate();
		jmsTempldate.setConnectionFactory(connectionFactory);
		JMS_TEMPLATE_HOLDER.put(brokerUrl, jmsTempldate);
		return jmsTempldate;
	}
}
