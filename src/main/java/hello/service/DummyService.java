package hello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.messaging.TextMessenger;

@Service
public class DummyService {

	private final Logger log = LoggerFactory.getLogger(DummyService.class);

	@Autowired
	private TextMessenger messageSender;

	public void logSomething(String text) {
		log.info("Logging this message: {}", text);
	}

	public void sendSomething(String text) {
		log.info("Sending this message: {}", text);
		messageSender.send(text);
	}

}
