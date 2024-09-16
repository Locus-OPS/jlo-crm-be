package th.co.locus.jlo.mail.receiver.config;

import java.util.Properties;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.integration.mail.MailReceivingMessageSource;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.mail.receiver.service.ReceiveMailService;

@Slf4j
@Configuration
@EnableIntegration
public class MailReceiverConfiguration {

	@Value("${mail.imap.ssl}")
	private boolean ssl;

	@Value("${mail.imap.host}")
	private String host;

	@Value("${mail.imap.port}")
	private String port;

	@Value("${mail.imap.username}")
	private String username;

	@Value("${mail.imap.password}")
	private String password;

	@Value("${mail.imap.mailbox}")
	private String mailbox;
	
	
	private final ReceiveMailService receiveMailService;

	public MailReceiverConfiguration(ReceiveMailService receiveMailService) {
		this.receiveMailService = receiveMailService;
	}

	@ServiceActivator(inputChannel = "receiveEmailChannel")
	public void receive(Message<?> message) {
		receiveMailService.handleReceivedMail((MimeMessage) message.getPayload());
	}

	@Bean("receiveEmailChannel")
	public DirectChannel defaultChannel() {
		DirectChannel directChannel = new DirectChannel();
		directChannel.setDatatypes(MimeMessage.class);
		return directChannel;
	}

	@Bean()
	@InboundChannelAdapter(channel = "receiveEmailChannel", poller = @Poller(fixedDelay = "${mail.imap.poller.fixedDelay}", taskExecutor = "asyncTaskExecutor"),autoStartup ="${mail.imap.receive.autoStartup}")
	public MailReceivingMessageSource mailMessageSource(MailReceiver mailReceiver) {
		MailReceivingMessageSource mailReceivingMessageSource = new MailReceivingMessageSource(mailReceiver);
		return mailReceivingMessageSource;
	}

	private String getImapUrl() {
		return String.format("imaps://%s:%s@%s:%s/%s", this.username, this.password, this.host, this.port,
				this.mailbox);
	}

	@Bean
	public MailReceiver imapMailReceiver() {
		String storeUrl = getImapUrl();
		log.info("IMAP connection url: {}", storeUrl);

		ImapMailReceiver imapMailReceiver = new ImapMailReceiver(storeUrl);
		imapMailReceiver.setShouldMarkMessagesAsRead(true);
		imapMailReceiver.setShouldDeleteMessages(false);
		imapMailReceiver.setMaxFetchSize(10);
		// imapMailReceiver.setAutoCloseFolder(true);

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailProperties.put("mail.imap.socketFactory.fallback", false);
		javaMailProperties.put("mail.store.protocol", "imaps");
		javaMailProperties.put("mail.debug", true);

		imapMailReceiver.setJavaMailProperties(javaMailProperties);
		log.info("IMAP imapMailReceiver : {}", imapMailReceiver);

		return imapMailReceiver;
	}

}
