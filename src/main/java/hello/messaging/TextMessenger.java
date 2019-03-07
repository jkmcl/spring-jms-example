package hello.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import hello.config.JmsProperties;

/**
 * Contains methods for synchronous sending and receiving of text messages
 */
@Component
public class TextMessenger {

	private final String outboundQueue;

	private final JmsTemplate jmsTemplate;

	public TextMessenger(JmsProperties properties, JmsTemplate template) {
		outboundQueue = properties.getQueue().getOutbound();
		jmsTemplate = template;
	}

	public void send(String text) {
		send(outboundQueue, text);
	}

	public void send(String destinationName, String text) {
		jmsTemplate.convertAndSend(destinationName, text);
	}

}
