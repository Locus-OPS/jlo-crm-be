package th.co.locus.jlo.websocket.chat.bean;

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
}
