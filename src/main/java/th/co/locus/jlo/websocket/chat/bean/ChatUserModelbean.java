package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatUserModelbean {
	private Long id;
	private String userId;
	private String userChatName;
	private String email;
	private String pictureUrl;
	private String roleName;
	private String positionName;
	private String message;
	private Long currentUserId;
	private Boolean checked=false;
	private Boolean isOnline;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date lastSeen;
}
