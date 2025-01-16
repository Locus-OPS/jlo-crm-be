package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatRoomModelBean {
	private Long roomId;
	private String roomName;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createdAt;
	private Long currentUserId;
}
