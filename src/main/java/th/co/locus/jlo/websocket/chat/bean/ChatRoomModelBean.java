package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatRoomModelBean {
	public Long roomId;
	public String roomName;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date createdAt;
	public Long currentUserId;
	public List<ChatUserModelbean> userList;
	public Boolean checked=false;
	public Long totalMember;
}
