package hello.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jms")
public class JmsProperties {

	private boolean sendBytes = false;

	private Charset charset = StandardCharsets.UTF_8;

	private Queue queue = new Queue();

	public boolean isSendBytes() {
		return sendBytes;
	}

	public void setSendBytes(boolean sendBytes) {
		this.sendBytes = sendBytes;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public static class Queue {

		private String inbound;

		private String outbound;

		private String command;

		public String getInbound() {
			return inbound;
		}

		public void setInbound(String inbound) {
			this.inbound = inbound;
		}

		public String getOutbound() {
			return outbound;
		}

		public void setOutbound(String outbound) {
			this.outbound = outbound;
		}

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}

	}

}
