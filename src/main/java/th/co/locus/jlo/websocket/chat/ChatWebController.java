package th.co.locus.jlo.websocket.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.websocket.chat.bean.ChatListModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatMessageModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatRoomMemberModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatRoomModelBean;
import th.co.locus.jlo.websocket.chat.bean.ChatUserModelbean;

@RestController
@RequestMapping("/api/chatweb")
public class ChatWebController extends BaseController {

	@Autowired
	private ChatWebService chatWebService;
	
	@PostMapping(value = "/getusers",produces = "application/json")
	public ApiPageResponse<List<ChatUserModelbean>> getUsers(@RequestBody ApiPageRequest<ChatUserModelbean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			request.getData().setCurrentUserId(getUserId());
			ServiceResult<Page<ChatUserModelbean>> results=chatWebService.getChatuserList(request.getData(), pageRequest);
			if(results.isSuccess()) {
				return ApiPageResponse.success(results.getResult().getContent(), results.getResult().getTotalElements());
			}
			return ApiPageResponse.fail("500",results.getResponseCode());
		}catch (Exception e) {
			return ApiPageResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/getchatroomlist",produces = "application/json")
	public ApiResponse<List<ChatRoomModelBean>> getChatRoomList(@RequestBody ApiPageRequest<ChatRoomModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			request.getData().setCurrentUserId(getUserId());
			ServiceResult<Page<ChatRoomModelBean>> result=chatWebService.getChatRoomListByUserId(request.getData(),pageRequest);
			if(result.isSuccess()) {
				return ApiPageResponse.success(result.getResult().getContent(),result.getResult().getTotalElements());
			}
			return ApiResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch (Exception e) {
			return ApiResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/createchatroom",produces = "application/json")
	public ApiResponse<ChatRoomModelBean> createChatRoom(@RequestBody ApiRequest<ChatRoomModelBean> request) {
		try {
			request.getData().setCurrentUserId(getUserId());
			ServiceResult<ChatRoomModelBean>  result=chatWebService.createChatRoom(request.getData());
			if(result.isSuccess()) {
				return ApiResponse.success(result.getResult());
			}
			return ApiResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/addusertochatroom",produces = "application/json")
	public ApiResponse<ChatRoomMemberModelBean> addUsertoChatRoom(@RequestBody ApiRequest<ChatRoomMemberModelBean> request) {
		try {
			ServiceResult<ChatRoomMemberModelBean> result=chatWebService.addUsertoRoom(request.getData());
			if(result.isSuccess()) {
				return ApiResponse.success(result.getResult());
			}
			return ApiResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/createchatmessage",produces = "application/json")
	public ApiResponse<ChatMessageModelBean> createChatMessage(@RequestBody ApiRequest<ChatMessageModelBean> request) {
		try {
			ServiceResult<ChatMessageModelBean> result=chatWebService.createChatMessage(request.getData());
			if(result.isSuccess()) {
				return ApiResponse.success(result.getResult());
			}
			return ApiResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/getprivatechatmessage",produces = "application/json")
	public ApiPageResponse<List<ChatMessageModelBean>> getPrivateChatMessage(@RequestBody ApiPageRequest<ChatMessageModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<ChatMessageModelBean>> result=chatWebService.getPrivateChatmessage(request.getData(), pageRequest);
			if(result.isSuccess()) {
				return ApiPageResponse.success(result.getResult().getContent(), result.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiPageResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/getpublicchatmessage",produces = "application/json")
	public ApiPageResponse<List<ChatMessageModelBean>> getPublicChatMessage(@RequestBody  ApiPageRequest<ChatMessageModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<ChatMessageModelBean>> result=chatWebService.getPublicChatmessage(request.getData(), pageRequest);
			if(result.isSuccess()) {
				return ApiPageResponse.success(result.getResult().getContent(), result.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiPageResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/getbroadcastchatmessage",produces = "application/json")
	public ApiPageResponse<List<ChatMessageModelBean>> getBroadcastChatMessage(@RequestBody  ApiPageRequest<ChatMessageModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<ChatMessageModelBean>> result=chatWebService.getBroadcastChatmessage(request.getData(), pageRequest);
			if(result.isSuccess()) {
				return ApiPageResponse.success(result.getResult().getContent(), result.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiPageResponse.fail("500",e.getMessage());
		}
	}
	
	@PostMapping(value="/getchatlist",produces = "application/json")
	public ApiPageResponse<List<ChatListModelBean>> getChatList(@RequestBody ApiPageRequest<ChatListModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			request.getData().setCurrentUser(getUserId());
			ServiceResult<Page<ChatListModelBean>> result=chatWebService.getChatList(request.getData(), pageRequest);
			if(result.isSuccess()) {
				return ApiPageResponse.success(result.getResult().getContent(), result.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(result.getResponseCode(),result.getResponseDescription());
		}catch(Exception e) {
			return ApiPageResponse.fail("500",e.getMessage());
		}
	}
	
	
	
}
