package th.co.locus.jlo.websocket.chat;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.websocket.chat.bean.ChatUserModelbean;
import th.co.locus.jlo.websocket.chat.bean.ChatUserStatusModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatMessageListModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatMessageModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatRoomMemberModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatRoomModelBean;

public interface ChatWebService {
	ServiceResult<Page<ChatUserModelbean>> getChatuserList(ChatUserModelbean bean,PageRequest pageRequest);
	ServiceResult<List<ChatRoomModelBean>> getChatRoomList();
	ServiceResult<Page<ChatRoomModelBean>> getChatRoomListByUserId(ChatRoomModelBean bean,PageRequest pageRequest);
	ServiceResult<ChatRoomModelBean> createChatRoom(ChatRoomModelBean bean);
	ServiceResult<ChatRoomModelBean> updateChatRoom(ChatRoomModelBean bean);
	ServiceResult<ChatRoomModelBean> getChatRoomById(ChatRoomModelBean bean);
	ServiceResult<ChatRoomModelBean> deleteChatRoom(ChatRoomModelBean bean);
	ServiceResult<ChatRoomMemberModelBean> addUsertoRoom(ChatRoomMemberModelBean bean);
	ServiceResult<ChatMessageModelBean> createChatMessage(ChatMessageModelBean bean);
	ServiceResult<Page<ChatMessageModelBean>> getPrivateChatmessage(ChatMessageModelBean bean,PageRequest pageRequest);
	ServiceResult<Page<ChatMessageModelBean>> getPublicChatmessage(ChatMessageModelBean bean,PageRequest pageRequest);
	ServiceResult<Page<ChatMessageModelBean>> getBroadcastChatmessage(ChatMessageModelBean bean,PageRequest pageRequest);
	ServiceResult<Page<ChatMessageListModelBean>> getChatList(ChatMessageListModelBean bean,PageRequest pageRequest);
	ServiceResult<ChatUserStatusModelBean> updateUserStatus(ChatUserStatusModelBean bean);
	
	
}
