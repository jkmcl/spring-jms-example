package hello.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import hello.service.DummyService;

@Component
public class InboundMessageProcessor extends AbstractMessageProcessor {

	private final Logger log = LoggerFactory.getLogger(InboundMessageProcessor.class);

	private final DummyService dummyService;

	public InboundMessageProcessor(JmsMessageConverter jmsMessageConverter, DummyService dummyService) {
		super(jmsMessageConverter);
		this.dummyService = dummyService;
	}

	@Override
	public void processMessage(String text) {
		log.info("Processing message...");
		dummyService.logSomething(text);
	}

}
