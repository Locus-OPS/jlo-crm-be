package th.co.locus.jlo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/message")
    public void message(@Payload Map<String, Object> payload, SimpMessageHeaderAccessor headerAccessor) {
        payload.put("sessionId", headerAccessor.getSessionId());
        log.info("Sending message to /topic/public: {}", payload);
        messagingTemplate.convertAndSend("/topic/public", payload);
        log.info("Sending message to /topic/public: {}", payload);
    }

}
