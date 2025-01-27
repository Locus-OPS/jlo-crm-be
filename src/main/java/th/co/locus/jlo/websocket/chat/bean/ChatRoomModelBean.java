package th.co.locus.jlo.websocket.chat.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomModelBean extends BaseModelBean{
	public Long roomId;
	public String roomName;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date createdAt;
	public String description;
	public Long currentUserId;
	public List<ChatUserModelbean> userList;
	public Boolean checked=false;
	public Long totalMember;
}
