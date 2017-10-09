package hello.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import hello.service.DummyService;

@Component
public class InboundMessageProcessor {

	private final Logger log = LoggerFactory.getLogger(InboundMessageProcessor.class);

	@Autowired
	DummyService dummyService;

	@Async
	public void process(Message message) {
		log.info("Processing message...");
		if (message instanceof TextMessage) {
			try {
				String text = ((TextMessage) message).getText();
				dummyService.logSomething(text);
			}
			catch (JMSException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			log.info("Message is not of type TextMessage");
		}
	}

}
