package th.co.locus.jlo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import th.co.locus.jlo.websocket.chat.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketChatConfig implements WebSocketConfigurer {

	private final ChatWebSocketHandler chatHandler;

	public WebSocketChatConfig(ChatWebSocketHandler chatHandler) {
		this.chatHandler = chatHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");
		registry.addHandler(chatHandler, "/chat").setAllowedOrigins("https://jlo.locus.co.th"); // ระบุ Origin ที่ใช้จริง

	}
}
