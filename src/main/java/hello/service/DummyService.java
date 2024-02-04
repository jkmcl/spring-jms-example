package hello.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hello.messaging.TextMessenger;

@Service
public class DummyService {

	private final Logger log = LoggerFactory.getLogger(DummyService.class);

	private final Queue<String> receivedMessages = new ConcurrentLinkedQueue<>();

	private final TextMessenger textMessenger;

	public DummyService(TextMessenger textMessenger) {
		this.textMessenger = textMessenger;
	}

	public void logSomething(String text) {
		log.info("Logging this message: {}", text);
		receivedMessages.add(text);
	}

	public void sendSomething(String text) {
		log.info("Sending this message: {}", text);
		textMessenger.send(text);
	}

	public String getReceivedMessage() {
		return receivedMessages.poll();
	}

	public int getReceivedMessageCount() {
		return receivedMessages.size();
	}

}
