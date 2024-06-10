package th.co.locus.jlo.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LineSfdcConfig {

	@Value("${line.msg.sticker_send}")
	private String stickerSendMsg;
	
	@Value("${line.msg.image_send}")
	private String imageSendMsg;
	
	@Value("${line.msg.session_end}")
	private String sessionEndMsg;
	
	@Value("${sfdc.chat.api_version}")
	private String apiVersion;
	
	@Value("${sfdc.chat.org_id}")
	private String orgId;
	
	@Value("${sfdc.chat.deployment_id}")
	private String deploymentId;
	
	@Value("${sfdc.chat.button_id}")
	private String buttonId;
	
	@Value("${sfdc.chat.polling_timeout}")
	private int pollingTimeout;
	
	@Value("${sfdc.msg.chat_init}")
	private String chatInitMessage;
	
	@Value("${sfdc.msg.chat_start}")
	private String chatStartMessage;
	
	@Value("${sfdc.msg.chat_end}")
	private String chatEndMessage;
	
	@Value("${sfdc.msg.unavailable}")
	private String unavailableMessage;
	
	@Value("${sfdc.chat.url}")
	private String chatUrl;
	
	@Value("${sfdc.chat.path.session}")
	private String sessionPath;
	
	@Value("${sfdc.chat.path.request}")
	private String requestPath;
	
	@Value("${sfdc.chat.path.messages}")
	private String messagesPath;
	
	@Value("${sfdc.chat.path.chat_message}")
	private String messagePath;
	
	@Value("${sfdc.chat.path.chat_end}")
	private String chatEndPath;
	
	public String getSessionUrl() {
		return this.chatUrl + this.sessionPath;
	}
	
	public String getRequestUrl() {
		return this.chatUrl + this.requestPath;
	}
	
	public String getMessagesUrl() {
		return this.chatUrl + this.messagesPath;
	}
	
	public String getMessageUrl() {
		return this.chatUrl + this.messagePath;
	}
	
	public String getChatEndUrl() {
		return this.chatUrl + this.chatEndPath;
	}
	
}
