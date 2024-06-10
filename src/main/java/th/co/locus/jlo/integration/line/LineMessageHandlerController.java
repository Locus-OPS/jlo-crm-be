package th.co.locus.jlo.integration.line;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import th.co.locus.jlo.integration.LineSfdcConfig;
import th.co.locus.jlo.integration.PrechatConfig;
import th.co.locus.jlo.integration.sfdc.SfdcSessionHandler;

@LineMessageHandler
public class LineMessageHandlerController {
	
	@Autowired
    private SfdcSessionHandler sfdcSessionHandler;
	
	@Autowired
	private LineSfdcConfig lineSfdcConfig;
	
	@Autowired
	private PrechatConfig prechatConfig;
	
	@Autowired
	private StateHandler stateHandler;

	@EventMapping
	public Message handleTextMessage(MessageEvent<TextMessageContent> e) {
		Message message = null;
		String command = e.getMessage().getText();		
		switch (command) {
		case "end":
			if (sfdcSessionHandler.isOnline(e.getSource().getUserId())) {
				sfdcSessionHandler.endChat(e.getSource().getUserId());
			}
			break;
		case "yes":
			if (sfdcSessionHandler.isOnline(e.getSource().getUserId())) {
				sfdcSessionHandler.checkAndSendMessage(e.getSource().getUserId(), e.getMessage().getText());
			} else {
				message = handleState(e.getSource().getUserId(), null);				
			}
			break;
		default:
			if (sfdcSessionHandler.isOnline(e.getSource().getUserId())) {
				sfdcSessionHandler.checkAndSendMessage(e.getSource().getUserId(), e.getMessage().getText());				
			} else {
				message = handleState(e.getSource().getUserId(), command);
			}
			break;
		}
		return message;
	}
	
	@EventMapping
	public Message handleStickerMessage(MessageEvent<StickerMessageContent> e) {
		if (sfdcSessionHandler.isOnline(e.getSource().getUserId())) {
			sfdcSessionHandler.checkAndSendMessage(e.getSource().getUserId(), lineSfdcConfig.getStickerSendMsg());				
			prechatConfig.getItems();
		}
		return null;
	}
	
	@EventMapping
	public Message handleImageMessage(MessageEvent<ImageMessageContent> e) {
		if (sfdcSessionHandler.isOnline(e.getSource().getUserId())) {
			sfdcSessionHandler.checkAndSendMessage(e.getSource().getUserId(), lineSfdcConfig.getImageSendMsg());				
		}
		return null;
	}

	@EventMapping
	public Message handlePostbackEvent(PostbackEvent event) {
		Message message = null;
		String postData = event.getPostbackContent().getData();
		String command = getCommand(postData);
		String data = getData(postData);
		switch (command) {	
		case "startchat":
//			sfdcSessionHandler.start(event.getSource().getUserId());
			message = handleState(event.getSource().getUserId(), null);
			break;
		case "donothing":
			break;		
		default:
			break;
		}		
		return message;
	}
	
	private Message handleState(String lineId, String text) {
		Map<String, String> stateMap;
		try {
			stateMap = stateHandler.get(lineId);
			String state = stateMap.get("state");
			if (state == null) {
				stateMap.put("state", "I");
				stateHandler.put(lineId, stateMap);
				return quickReplyStartChat();			
			} else if ("I".equals(state)) {
				if (prechatConfig.isEmpty()) {
					return initSfdcConnection(lineId, stateMap);
				} else {					
					stateMap.put("state", "S");
					stateMap.put("count", "1");
					stateHandler.put(lineId, stateMap);
					return new TextMessage(prechatConfig.getItems().get(0).getQuestion());
				}
			} else if ("S".equals(state)) {
				int count = Integer.parseInt(stateMap.get("count"));
				stateMap.put(prechatConfig.getItems().get(count - 1).getLabel(), text);
				if (count == prechatConfig.getItems().size()) {					
					return initSfdcConnection(lineId, stateMap);
				} else {
					stateMap.put("count", String.valueOf(count + 1));
					stateHandler.put(lineId, stateMap);
					return new TextMessage(prechatConfig.getItems().get(count).getQuestion());
				}
			} else {
				return null;
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
			return new TextMessage("Unexpected Error");
		}		
	}
	
	private Message initSfdcConnection(String lineId, Map<String, String> paramMap) {
		sfdcSessionHandler.start(lineId, paramMap);			
		stateHandler.delete(lineId);
		return new TextMessage(lineSfdcConfig.getChatStartMessage());
	}
	
	private TextMessage quickReplyStartChat() {
		return new TextMessage(lineSfdcConfig.getChatInitMessage());
//		List<QuickReplyItem> items = Arrays.asList(
//	      QuickReplyItem.builder()
//	             .action(new PostbackAction("ต้องการ", "startchat"))
//	             .build(),
//	      QuickReplyItem.builder()
//	             .action(new PostbackAction("ไม่ต้องการ", "donothing"))
//	             .build()
//				);
//		TextMessage target = TextMessage
//	      .builder()
//	      .text("คุณต้องการเริ่มต้นการสนทนากับเจ้าหน้าที่หรือไม่")
//	      .quickReply(QuickReply.items(items))
//	      .build();
//		return target;
	}
	
	private String getCommand(String message) {
		if (message.indexOf(":") != -1) {
			return message.split(":")[0];
		} else {
			return message;
		}
	}

	private String getData(String message) {
		if (message.indexOf(":") != -1) {
			return message.split(":")[1];
		} else {
			return "";
		}
	}

}
