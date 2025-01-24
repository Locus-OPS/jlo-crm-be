package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatMessageListModelBean {
	private Long id;
	private String pictureUrl;
	private String chatName;
	private Long lastMessageId;
	private String messageText;
	private String messageStatus;
	private String messageType;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createdAt;
	private Long currentUser;
	private Boolean isOnline;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date lastSeen;
}
