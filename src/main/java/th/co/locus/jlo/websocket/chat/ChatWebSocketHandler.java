package th.co.locus.jlo.websocket.chat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.system.user.UserService;
import th.co.locus.jlo.websocket.chat.bean.ChatMessageModelBean;
import th.co.locus.jlo.system.user.dto.UserLoginDTO;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.websocket.chat.bean.ChatUserStatusModelBean;
/**
 * @author Apichat
 */
@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private ChatWebService chatService;
	@Autowired
	private UserService userService;

	// Map to store users in each room (group chat)
	private final Map<String, Set<WebSocketSession>> rooms = new HashMap<>();
	
	// Map to store username to WebSocketSession (1:1 chat)
	private final Map<String, WebSocketSession> users = new HashMap<>();
	
    // เก็บ WebSocket sessions และข้อมูลเกี่ยวกับห้องสนทนา
    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

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
			//session.sendMessage(new TextMessage("Welcome to JLO CRM Broadcast!"));
			// broadcastToAll("A new user has joined the chat!");
			ChatUserStatusModelBean status=new ChatUserStatusModelBean();
			status.setUserId(username==null? 0:Long.parseLong(username));
			status.setIsOnline(true);
			chatService.updateUserStatus(status);
			
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
			}else if (payload.startsWith("/broadcast")) {
		        // เรียกใช้ broadcastToAll
		        String broadcastMessage = payload.substring(11); // ตัด "/broadcast "
		        broadcastToAll("Broadcast: " + broadcastMessage);
		    }  else {
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
			
			ChatUserStatusModelBean userStatus=new ChatUserStatusModelBean();
			userStatus.setUserId(username==null? 0:Long.parseLong(username));
			userStatus.setIsOnline(false);
			chatService.updateUserStatus(userStatus);
		}
		rooms.values().forEach(sessions -> sessions.remove(session));
	}

	private void joinRoom(WebSocketSession session, String roomName) throws Exception {
		// Remove user from all current rooms
		rooms.values().forEach(sessions -> sessions.remove(session));

		// Add user to the new room
		rooms.putIfAbsent(roomName, new HashSet<>());
		rooms.get(roomName).add(session);

//		session.sendMessage(new TextMessage("You joined room: " + roomName));
	}

	private void sendPrivateMessage(WebSocketSession sender, String targetUsername, String message) throws Exception {
		log.debug("sendPrivateMessage {} ,{}",targetUsername,message);
		WebSocketSession targetSession = users.get(targetUsername);
		String senderUsername = getUsernameFromSession(sender);
		try {
			
			if (targetSession != null && targetSession.isOpen()) {
				
//				targetSession.sendMessage(new TextMessage("[Private from " + senderUsername + "]: " + message));
				
				 // รับเวลาปัจจุบัน
		        LocalDateTime now = LocalDateTime.now();
		        // กำหนดฟอร์แมตวันที่
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		        // แปลงเวลาปัจจุบันเป็น String
		        String formattedDate = now.format(formatter);
		        
				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{")
		           .append("\"messageId\":\"0\",")
		           .append("\"senderId\":\""+senderUsername+"\",")
		           .append("\"senderName\":\""+"Sathaporn"+"\",")
		           .append("\"pictureUrl\":\""+"pictureUrl"+"\",")
		           .append("\"roomId\":\"\",")
		           .append("\"targetId\":\""+targetUsername+"\",")
		           .append("\"messageText\":\""+message+"\",")
		           .append("\"messageType\":\"private\",")
		           .append("\"messageStatus\":\"sent\",")
		           .append("\"createdAt\":\""+formattedDate+"\"")
		           .append("}");
				String jsonString = jsonBuilder.toString();

				targetSession.sendMessage(new TextMessage(jsonString));

				
			} 
//			else {
//				sender.sendMessage(new TextMessage("User " + targetUsername + " is not available."));
//			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}finally {
			//Save Message to Database
			ChatMessageModelBean msg=new ChatMessageModelBean();
			msg.setSenderId(Long.valueOf(senderUsername));
			msg.setTargetId(Long.valueOf(targetUsername));
			msg.setMessageType("private");
			msg.setMessageText(message);
			msg.setMessageStatus("sent");
			chatService.createChatMessage(msg);
		}
		
		
	}

	private void broadcastMessage(WebSocketSession sender, String message) throws Exception {
		log.info("broadcastMessage {} ",message);
		// Find the room the sender is in
		String senderRoom = rooms.entrySet().stream().filter(entry -> entry.getValue().contains(sender))
				.map(Map.Entry::getKey).findFirst().orElse("general");
		String senderUsername = getUsernameFromSession(sender);
		
        try {
   		 	// รับเวลาปัจจุบัน
            LocalDateTime now = LocalDateTime.now();
            // กำหนดฟอร์แมตวันที่
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            // แปลงเวลาปัจจุบันเป็น String
            String formattedDate = now.format(formatter);
            
            String senderName="",pictureUrl="";
            ServiceResult<UserLoginDTO> senderResult=userService.getUserById(senderUsername);
            if(senderResult.isSuccess()) {
            	if(senderResult.getResult()!=null) {
            		senderName=senderResult.getResult().getFirstName()+" "+senderResult.getResult().getLastName();
            		pictureUrl=senderResult.getResult().getPictureUrl()==null? "":senderResult.getResult().getPictureUrl();
            	}
            }
            
    		// Broadcast message to everyone in the room
    		for (WebSocketSession session : rooms.getOrDefault(senderRoom, new HashSet<>())) {
    			if (session.isOpen() && !session.equals(sender)) {
    				StringBuilder jsonBuilder = new StringBuilder();
    				jsonBuilder.append("{")
    		           .append("\"messageId\":\"0\",")
    		           .append("\"senderId\":\""+senderUsername+"\",")
    		           .append("\"senderName\":\""+senderName+"\",")
    		           .append("\"pictureUrl\":\""+pictureUrl+"\",")
    		           .append("\"roomId\":\""+senderRoom+"\",")
    		           .append("\"targetId\":\"\",")
    		           .append("\"messageText\":\""+message+"\",")
    		           .append("\"messageType\":\"public\",")
    		           .append("\"messageStatus\":\"sent\",")
    		           .append("\"createdAt\":\""+formattedDate+"\"")
    		           .append("}");
    				String jsonString = jsonBuilder.toString();
    				session.sendMessage(new TextMessage(jsonString));
    				//session.sendMessage(new TextMessage("[From " + senderUsername + "]: " + message));
    			}
    		}
        }catch(Exception ex) {
        	log.error(ex.getMessage());
        }finally {
    		ChatMessageModelBean msg=new ChatMessageModelBean();
    		msg.setSenderId(Long.valueOf(senderUsername));
    		msg.setRoomId(Long.valueOf(senderRoom));
    		msg.setMessageType("public");
    		msg.setMessageText(message);
    		msg.setMessageStatus("sent");
    		chatService.createChatMessage(msg);
		}

		

	}
	
	 // Method สำหรับ Broadcast ไปยังทุกคนในระบบ
    private void broadcastToAll(String message) {
    	
        sessions.keySet().forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


	private String getUsernameFromSession(WebSocketSession session) {
		// Example: Assume the username is passed as a query parameter "username"
		return session.getUri() != null ? session.getUri().getQuery().split("=")[1] : null;
	}
}
