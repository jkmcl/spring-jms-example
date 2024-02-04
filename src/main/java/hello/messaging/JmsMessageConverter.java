package hello.messaging;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import hello.config.JmsProperties;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Component
public class JmsMessageConverter implements MessageConverter {

	private final Logger log = LoggerFactory.getLogger(JmsMessageConverter.class);

	private boolean sendBytes = false;

	private Charset charset = StandardCharsets.UTF_8;

	public JmsMessageConverter(JmsProperties jmsProperties) {
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

		if (message instanceof TextMessage textMessage) {
			text = textMessage.getText();
		} else if (message instanceof BytesMessage bytesMessage) {
			byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(bytes);
			text = new String(bytes, charset);
		} else {
			throw new MessageConversionException("Unsupported JMS message type");
		}

		return text;
	}

}
