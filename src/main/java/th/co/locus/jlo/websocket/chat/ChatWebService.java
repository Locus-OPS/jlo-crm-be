package th.co.locus.jlo.websocket.chat;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.websocket.chat.bean.ChatUserModelbean;

public interface ChatWebService {
	ServiceResult<Page<ChatUserModelbean>> getChatuserList(ChatUserModelbean bean,PageRequest pageRequest);
}
