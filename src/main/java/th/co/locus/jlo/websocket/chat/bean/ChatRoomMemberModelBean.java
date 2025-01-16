package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatRoomMemberModelBean {
	private Long userId;
	private Long roomId;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date joinAt;
}
