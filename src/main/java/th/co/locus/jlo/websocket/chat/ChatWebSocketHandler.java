package th.co.locus.jlo.websocket.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Apichat
 */
@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	// Map to store users in each room (group chat)
	private final Map<String, Set<WebSocketSession>> rooms = new HashMap<>();
	
	// Map to store username to WebSocketSession (1:1 chat)
	private final Map<String, WebSocketSession> users = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// Default: assign users to a "general" room
		String username = getUsernameFromSession(session);
		log.debug("username {} ",username);
		log.debug("Printing username value: {}", username);
		if (username != null) {
			users.put(username, session); // Add to users map
			rooms.putIfAbsent("general", new HashSet<>());
			rooms.get("general").add(session);
//			session.sendMessage(new TextMessage("Welcome to the general room!"));
		} else {
			session.close();
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();

		if (payload.startsWith("/join ")) {
			// Join a room (group chat)
			String roomName = payload.substring(6).trim();
			joinRoom(session, roomName);
			log.debug("join {} ",roomName);
		} else if (payload.startsWith("/private ")) {
			// Send a private message: /private username message
			String[] parts = payload.split(" ", 3);
			if (parts.length == 3) {
				String targetUsername = parts[1];
				String privateMessage = parts[2];
				sendPrivateMessage(session, targetUsername, privateMessage);
				log.debug("private targetUsername{}  privateMessage{}",targetUsername,privateMessage);
			} else {
				session.sendMessage(new TextMessage("Invalid private message format. Use: /private username message"));
			}
		} else {
			// Broadcast message to the room (group chat)
			log.debug("broadcastMessage {}",payload);
			broadcastMessage(session, payload);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
			throws Exception {
		// Remove user from all rooms and users map
		String username = getUsernameFromSession(session);
		if (username != null) {
			users.remove(username);
		}
		rooms.values().forEach(sessions -> sessions.remove(session));
	}

	private void joinRoom(WebSocketSession session, String roomName) throws Exception {
		// Remove user from all current rooms
		rooms.values().forEach(sessions -> sessions.remove(session));

		// Add user to the new room
		rooms.putIfAbsent(roomName, new HashSet<>());
		rooms.get(roomName).add(session);

		session.sendMessage(new TextMessage("You joined room: " + roomName));
	}

	private void sendPrivateMessage(WebSocketSession sender, String targetUsername, String message) throws Exception {
		log.debug("sendPrivateMessage {} ,{}",targetUsername,message);
		WebSocketSession targetSession = users.get(targetUsername);

		if (targetSession != null && targetSession.isOpen()) {
			String senderUsername = getUsernameFromSession(sender);
			targetSession.sendMessage(new TextMessage("[Private from " + senderUsername + "]: " + message));
		} else {
			sender.sendMessage(new TextMessage("User " + targetUsername + " is not available."));
		}
	}

	private void broadcastMessage(WebSocketSession sender, String message) throws Exception {
		log.info("broadcastMessage {} ",message);
		// Find the room the sender is in
		String senderRoom = rooms.entrySet().stream().filter(entry -> entry.getValue().contains(sender))
				.map(Map.Entry::getKey).findFirst().orElse("general");

		// Broadcast message to everyone in the room
		for (WebSocketSession session : rooms.getOrDefault(senderRoom, new HashSet<>())) {
			if (session.isOpen() && !session.equals(sender)) {
				String senderUsername = getUsernameFromSession(sender);
				session.sendMessage(new TextMessage("[From " + senderUsername + "]: " + message));
			}
		}
	}

	private String getUsernameFromSession(WebSocketSession session) {
		// Example: Assume the username is passed as a query parameter "username"
		return session.getUri() != null ? session.getUri().getQuery().split("=")[1] : null;
	}
}
