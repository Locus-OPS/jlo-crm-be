package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatMessageModelBean {
	private Long messageId;
	private Long senderId;
	private String senderName;
	private String pictureUrl;
	private Long roomId;
	private Long targetId;
	private String messageText;
	private String messageType;
	private String messageStatus;
//	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date createdAt;
	private Long lastMessageId=0L;
}
