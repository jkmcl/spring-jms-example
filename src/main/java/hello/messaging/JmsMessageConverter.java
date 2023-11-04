package hello.messaging;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import hello.config.JmsProperties;

@Component
public class JmsMessageConverter implements MessageConverter {

	private final Logger log = LoggerFactory.getLogger(JmsMessageConverter.class);

	private boolean sendBytes = false;

	private Charset charset = StandardCharsets.UTF_8;

	@Autowired
	private JmsProperties jmsProperties;

	@PostConstruct
	public void init() {
		sendBytes = jmsProperties.isSendBytes();
		charset = jmsProperties.getCharset();
		log.debug("sendBytes: {}; charset: {}", sendBytes, charset);
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		log.debug("Converting from String to Message...");

		String text = (String) object;

		if (!sendBytes) {
			return session.createTextMessage(text);
		}

		byte[] bytes = text.getBytes(charset);
		BytesMessage bytesMessage = session.createBytesMessage();
		bytesMessage.writeBytes(bytes);
		return bytesMessage;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		log.debug("Converting from Message to String...");

		String text;

		if (message instanceof TextMessage) {
			text = ((TextMessage) message).getText();
		} else if (message instanceof BytesMessage) {
			BytesMessage bytesMessage = ((BytesMessage) message);
			byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(bytes);
			text = new String(bytes, charset);
		} else {
			throw new MessageConversionException("Unsupported JMS message type");
		}

		return text;
	}

}
