package th.co.locus.jlo.integration.chat;

import org.springframework.stereotype.Service;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.integration.chat.bean.ChatUser;

import java.util.Map;

@Service
public class ChatService extends BaseService {

    public ChatUser saveChatUser(ChatUser chatUser) {
        commonDao.insert("integration-chat.saveChatUser", chatUser);
        return chatUser;
    }

    public void blockChatUser(Long channelId, String userId) {
        commonDao.update("integration-chat.blockChatUser", Map.of("channelId", channelId, "userId", userId));
    }

}
