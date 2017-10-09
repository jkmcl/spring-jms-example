package hello.messaging;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.service.Command;

@Component
public class CommandMessageProcessor {

	private final Logger log = LoggerFactory.getLogger(CommandMessageProcessor.class);

	private final ObjectMapper mapper = new ObjectMapper();

	@Async
	public void process(Message message) {
		log.info("Processing message...");
		if (!(message instanceof TextMessage)) {
			log.error("Message is not of type {}", TextMessage.class.getName());
			return;
		}
		try {
			String text = ((TextMessage) message).getText();
			Command command = mapper.readValue(text, Command.class);
			log.info("Command received: {}", command.getName());
			command.getParameters().forEach((k,v) -> log.info("name: {}; value: {}", k, v));
		}
		catch (JMSException | IOException e) {
			throw new RuntimeException("Failed to extract text from message", e);
		}
	}

}
