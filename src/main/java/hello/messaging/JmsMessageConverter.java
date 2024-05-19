package hello.messaging;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import hello.config.JmsProperties;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Component
public class JmsMessageConverter implements MessageConverter {

	private final Logger log = LoggerFactory.getLogger(JmsMessageConverter.class);

	private final boolean sendBytes;

	private final Charset charset;

	public JmsMessageConverter(JmsProperties jmsProperties) {
		sendBytes = jmsProperties.isSendBytes();
		charset = jmsProperties.getCharset();
		log.debug("sendBytes: {}; charset: {}", sendBytes, charset);
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		log.debug("Converting String to Message...");

		if (object instanceof String text) {
			if (!sendBytes) {
				return session.createTextMessage(text);
			} else {
				var bytesMessage = session.createBytesMessage();
				bytesMessage.writeBytes(text.getBytes(charset));
				return bytesMessage;
			}
		}

		throw new MessageConversionException("Unsupported object type: " + ObjectUtils.nullSafeClassName(object));
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		log.debug("Converting Message to String...");

		if (message instanceof TextMessage textMessage) {
			return textMessage.getText();
		} else if (message instanceof BytesMessage bytesMessage) {
			var bytes = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(bytes);
			return new String(bytes, charset);
		}

		throw new MessageConversionException("Unsupported message type: " + ObjectUtils.nullSafeClassName(message));
	}

}
