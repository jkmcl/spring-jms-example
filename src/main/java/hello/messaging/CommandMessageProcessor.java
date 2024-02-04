package hello.messaging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.service.Command;

@Component
public class CommandMessageProcessor extends AbstractMessageProcessor {

	private final Logger log = LoggerFactory.getLogger(CommandMessageProcessor.class);

	private final ObjectMapper mapper = new ObjectMapper();

	private final Queue<Command> receivedCommands = new ConcurrentLinkedQueue<>();

	public CommandMessageProcessor(JmsMessageConverter jmsMessageConverter) {
		super(jmsMessageConverter);
	}

	@Override
	public void processMessage(String text) throws Exception {
		log.info("Processing message...");
		Command command;
		command = mapper.readValue(text, Command.class);
		log.info("Command received: {}", command.getName());
		command.getParameters().forEach((k, v) -> log.info("name: {}; value: {}", k, v));
		receivedCommands.add(command);
	}

	public Command getReceivedCommand() {
		return receivedCommands.poll();
	}

}
