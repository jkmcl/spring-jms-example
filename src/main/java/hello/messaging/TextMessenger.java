package hello.messaging;

import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

import hello.config.JmsProperties;

/**
 * Contains methods for synchronous sending and receiving of text messages
 */
@Component
public class TextMessenger {

	private final JmsOperations operations;

	private final String outboundQueue;

	private final String inboundQueue;

	public TextMessenger(JmsProperties properties, JmsOperations operations) {
		this.operations = operations;
		outboundQueue = properties.getQueue().getOutbound();
		inboundQueue = properties.getQueue().getInbound();
	}

	public void send(String text) {
		send(outboundQueue, text);
	}

	public void send(String destinationName, String text) {
		operations.convertAndSend(destinationName, text);
	}

	public String receive() {
		return receive(inboundQueue);
	}

	public String receive(String destinationName) {
		return (String) operations.receiveAndConvert(destinationName);
	}

}
