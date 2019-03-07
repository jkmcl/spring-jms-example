package hello.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMessageProcessor implements MessageListener {

	private final Logger log = LoggerFactory.getLogger(AbstractMessageProcessor.class);

	@Autowired
	private JmsMessageConverter jmsMessageConverter;

	@Override
	public void onMessage(Message message) {
		try {
			processMessage((String) jmsMessageConverter.fromMessage(message));
		} catch (Exception e) {
			log.error("Unable to process message", e);
		}
	}

	public abstract void processMessage(String message) throws Exception;

}
