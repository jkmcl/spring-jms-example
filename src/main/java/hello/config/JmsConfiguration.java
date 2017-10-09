package hello.config;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import hello.messaging.CommandMessageListener;
import hello.messaging.InboundMessageListener;

@Configuration
@EnableJms
@EnableConfigurationProperties(JmsProperties.class)
public class JmsConfiguration implements JmsListenerConfigurer {

	@Autowired
	private InboundMessageListener inboundMessageListener;

	@Autowired
	private CommandMessageListener commandMessageListener;

	@Autowired
	private JmsProperties properties;

	@Override
	public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
		SimpleJmsListenerEndpoint inboundEndpoint = new SimpleJmsListenerEndpoint();
		inboundEndpoint.setId(properties.getQueue().getInbound());
		inboundEndpoint.setDestination(properties.getQueue().getInbound());
		inboundEndpoint.setMessageListener(inboundMessageListener);
		registrar.registerEndpoint(inboundEndpoint);

		SimpleJmsListenerEndpoint commandEndpoint = new SimpleJmsListenerEndpoint();
		commandEndpoint.setId(properties.getQueue().getCommand());
		commandEndpoint.setDestination(properties.getQueue().getCommand());
		commandEndpoint.setMessageListener(commandMessageListener);
		registrar.registerEndpoint(commandEndpoint);
	}

	@Bean
	@ConditionalOnProperty(name="activemq.enabled", havingValue="false", matchIfMissing=false)
	public ConnectionFactory connectionFactory() {
		return null;
	}

}
