package hello.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;

public abstract class AbstractMessageProcessor implements MessageListener {

	private final Logger log = LoggerFactory.getLogger(AbstractMessageProcessor.class);

	private final JmsMessageConverter jmsMessageConverter;

	protected AbstractMessageProcessor(JmsMessageConverter jmsMessageConverter) {
		this.jmsMessageConverter = jmsMessageConverter;
	}

	@Override
	public void onMessage(Message message) {
		try {
			processMessage((String) jmsMessageConverter.fromMessage(message));
		} catch (Exception e) {
			log.error("Failed to process message", e);
		}
	}

	public abstract void processMessage(String message) throws Exception;

}
