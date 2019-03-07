package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.config.JmsProperties;
import hello.messaging.TextMessenger;
import hello.service.Command;
import hello.service.DummyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	private final Logger log = LoggerFactory.getLogger(ApplicationTest.class);

	@Autowired
	TextMessenger messenger;

	@Autowired
	DummyService service;

	@Autowired
	JmsProperties properties;

	@Test
	public void testSendMessage() {
		log.info("Sending 1 outbound message...");
		service.sendSomething("This message was sent via the service object");
	}

	@Test
	public void testInboundMessage() throws Exception {
		log.info("Sending 1 inbound message...");
		messenger.send(properties.getQueue().getInbound(), "Hello world!");

		Thread.sleep(3000);
	}

	@Test
	public void testMultipleInboundMessages() throws Exception {
		log.info("Sending 8 inbound messages...");
		messenger.send(properties.getQueue().getInbound(), "msg1");
		messenger.send(properties.getQueue().getInbound(), "msg2");
		messenger.send(properties.getQueue().getInbound(), "msg3");
		messenger.send(properties.getQueue().getInbound(), "msg4");
		messenger.send(properties.getQueue().getInbound(), "msg5");
		messenger.send(properties.getQueue().getInbound(), "msg6");
		messenger.send(properties.getQueue().getInbound(), "msg7");
		messenger.send(properties.getQueue().getInbound(), "msg8");

		Thread.sleep(3000);
	}

	@Test
	public void testCommandMessage() throws Exception {
		log.info("Sending 1 command message...");

		Command command = new Command();
		command.setName("doWork");
		command.putParameter("param1", "value1");
		command.putParameter("param2", "value2");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(command);
		log.info("JSON: {}", json);

		messenger.send(properties.getQueue().getCommand(), json);

		Thread.sleep(3000);
	}

}
