package th.co.locus.jlo.websocket.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.system.user.UserService;
import th.co.locus.jlo.system.user.bean.UserDataModelBean;
import th.co.locus.jlo.system.user.bean.UserListCriteriaModelBean;
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
	
}
