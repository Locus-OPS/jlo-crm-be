package th.co.locus.jlo.websocket.chat;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.websocket.chat.bean.ChatUserModelbean;

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

}
