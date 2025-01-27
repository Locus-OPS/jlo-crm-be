package th.co.locus.jlo.websocket.chat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.websocket.chat.bean.ChatMessageListModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatMessageModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatRoomMemberModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatRoomModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatUserModelbean;
import th.co.locus.jlo.websocket.chat.bean.ChatUserStatusModelBean;

@Service
public class ChatWebServiceImpl extends BaseService implements ChatWebService {

	@Override
	public ServiceResult<Page<ChatUserModelbean>> getChatuserList(ChatUserModelbean bean, PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("chatuser.getUserList", bean, pageRequest));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<ChatRoomModelBean>> getChatRoomList() {
		try {
			return success(commonDao.selectList("chatweb.getChatRoomList"));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<ChatRoomModelBean>> getChatRoomListByUserId(ChatRoomModelBean bean,PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("chatweb.getChatRoomListByUserId",bean,pageRequest));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<ChatRoomModelBean> createChatRoom(ChatRoomModelBean bean) {
		try {
			int result=commonDao.insert("chatweb.createChatRoom", bean);

			if(result>0) {
				ChatRoomMemberModelBean member=new ChatRoomMemberModelBean();
				member.setRoomId(bean.getRoomId());
				member.setUserId(bean.getCurrentUserId());
				this.addUsertoRoom(member);
				
				List<ChatUserModelbean> userList=bean.getUserList();
				for (ChatUserModelbean user : userList) {
					if(!user.getChecked()) {
						continue;
					}
					ChatRoomMemberModelBean member1=new ChatRoomMemberModelBean();
					member1.setRoomId(bean.getRoomId());
					member1.setUserId(user.getId());
					this.addUsertoRoom(member1);
				}
				return success(bean);
			}
			return fail("500","Unable to create chat room.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<ChatRoomMemberModelBean> addUsertoRoom(ChatRoomMemberModelBean bean) {
		try {
			int result=commonDao.insert("chatweb.addUsertoRoom",bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to add user to chat room.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<ChatMessageModelBean> createChatMessage(ChatMessageModelBean bean) {
		try {
			int result=commonDao.insert("chatweb.createChatMessage",bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create chat message.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<ChatMessageModelBean>> getPrivateChatmessage(ChatMessageModelBean bean,
			PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("chatweb.getPrivateChatMessageList", bean, pageRequest));
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<ChatMessageModelBean>> getPublicChatmessage(ChatMessageModelBean bean,
			PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("chatweb.getPublicChatMessageList", bean, pageRequest));
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<ChatMessageModelBean>> getBroadcastChatmessage(ChatMessageModelBean bean,
			PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("chatweb.getBroadcastChatMessageList", bean, pageRequest));
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<ChatMessageListModelBean>> getChatList(ChatMessageListModelBean bean,PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("chatweb.getChatList", bean, pageRequest));
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	}

	@Override
	public ServiceResult<ChatUserStatusModelBean> updateUserStatus(ChatUserStatusModelBean bean) {
		try {
			ChatUserStatusModelBean userStatus=commonDao.selectOne("chatweb.getUserStatus", bean);
			if(userStatus!=null) {
				commonDao.update("chatweb.updateUserStatus", bean);
			}else {
				commonDao.insert("chatweb.createUserStatus", bean);
			}
			
			return success(commonDao.selectOne("chatweb.getUserStatus", userStatus));
			
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	}

	@Override
	public ServiceResult<ChatRoomModelBean> updateChatRoom(ChatRoomModelBean bean) {
		try {
			int result=commonDao.update("chatweb.updateChatRoom", bean);
			if(result>0) {
				commonDao.delete("chatweb.deleteUserFromRoom", bean);
				
				List<ChatUserModelbean> userList=bean.getUserList();
				for (ChatUserModelbean user : userList) {
					if(!user.getChecked()) {
						continue;
					}
					ChatRoomMemberModelBean member1=new ChatRoomMemberModelBean();
					member1.setRoomId(bean.getRoomId());
					member1.setUserId(user.getId());
					this.addUsertoRoom(member1);
				}
				return success(bean);
			}
			return fail("500","Unable to update chat room.");
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	
	}

	@Override
	public ServiceResult<ChatRoomModelBean> getChatRoomById(ChatRoomModelBean bean) {
		try {
			ChatRoomModelBean chatRoom=commonDao.selectOne("chatweb.getChatRoom", bean);
			if(chatRoom!=null) {
				List<ChatUserModelbean> userRoomList=commonDao.selectList("chatweb.getUsersListFromRoomId", Map.of("roomId",bean.getRoomId()));
				if(userRoomList.size()>0) {
					chatRoom.setUserList(userRoomList);
				}
				return success(chatRoom);
			}
			return fail("500","Not found chat room.");
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
	}

	@Override
	public ServiceResult<ChatRoomModelBean> deleteChatRoom(ChatRoomModelBean bean) {
		try {
			int result=commonDao.delete("chatweb.deleteChatRoom", bean);
			if(result>0) {
				commonDao.delete("chatweb.deleteChatMessage", bean);
				commonDao.delete("chatweb.deleteUserFromRoom", bean);
				return success(bean);
			}
			return fail("500","Unable to delete chat room.");
		}catch(Exception e) {
			return fail("500",e.getMessage());
		}
		
	}

}
