package hello.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandMessageListener implements MessageListener {

	private final Logger log = LoggerFactory.getLogger(CommandMessageListener.class);

	@Autowired
	private CommandMessageProcessor msgProcessor;

	@Override
	public void onMessage(Message message) {
		log.info("Dispatching message to an async method...");
		msgProcessor.process(message);
	}

}
