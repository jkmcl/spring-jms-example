package hello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import hello.service.Command;

class JsonTest {

	private final Logger log = LoggerFactory.getLogger(JsonTest.class);

	@Test
	void test() throws Exception {
		Command inCmd = new Command();
		inCmd.setName("doWork");
		inCmd.putParameter("param1", "value1");
		inCmd.putParameter("param2", "value2");

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json = mapper.writeValueAsString(inCmd);
		log.info("JSON: {}", json);

		Command outCmd = mapper.readValue(json, Command.class);
		assertEquals(inCmd.getName(), outCmd.getName());
		assertEquals(inCmd.getParameter("param1"), outCmd.getParameter("param1"));
		assertEquals(inCmd.getParameter("param2"), outCmd.getParameter("param2"));

		log.info("Command: {}; Parameters:", outCmd.getName());
		outCmd.getParameters().forEach((k,v) -> log.info("{}:{}", k, v));
	}

}
