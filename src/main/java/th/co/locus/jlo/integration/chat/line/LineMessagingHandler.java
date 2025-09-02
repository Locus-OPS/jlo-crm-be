package th.co.locus.jlo.integration.chat.line;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.messaging.model.UserProfileResponse;
import com.linecorp.bot.webhook.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import th.co.locus.jlo.integration.chat.ChatService;
import th.co.locus.jlo.integration.chat.bean.ChatChannelConfig;
import th.co.locus.jlo.integration.chat.bean.ChatUser;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class LineMessagingHandler {

    @Autowired
    private LineMessagingService lineMessagingService;

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private ChatService chatService;

    @PostMapping("${line.webhook.path:/api/integration/line/webhook}/{channelKey}")
    public void handleLineWebhook(@PathVariable String channelKey, @RequestBody String body, @RequestHeader HttpHeaders headers) throws Exception {
        if (!validateSignature(headers, body, channelKey)) {
            return;
        }
        try {
            ObjectMapper om = getObjectMapper();
            JsonNode requestBody = om.readTree(body);
            log.info("Received Line Webhook {}", requestBody);

            requestBody.get("events").forEach(o -> {
                try {
                    Event event = om.treeToValue(o, Event.class);
                    if (event instanceof MessageEvent messageEvent) {
                        handleMessageContent(channelKey, messageEvent.message(), event.source());
                    } else if (event instanceof FollowEvent followEvent) {
                        UserProfileResponse profile = lineMessagingClient.getProfile(channelKey, event.source().userId()).get().body();
                        log.info("Profile {}", profile);
                        saveChatUser(channelKey, profile);

                        if (followEvent.follow().isUnblocked()) {
                            ReplyMessageRequest replyMessage = new ReplyMessageRequest(followEvent.replyToken(), List.of(new TextMessage.Builder("Welcome back, " + profile.displayName()).build()), false);
                            lineMessagingClient.replyMessage(channelKey, replyMessage);
                        }
                    } else if (event instanceof UnfollowEvent) {
                        log.info("UnfollowEvent {}", event);
                        final Long channelId = getChannelId(channelKey);
                        chatService.blockChatUser(channelId, event.source().userId());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    private void handleMessageContent(String channelId, MessageContent messageContent, Source source) throws ExecutionException {
        log.info("messageContent {}", messageContent);
        if (messageContent instanceof TextMessageContent) {
//            PushMessageRequest pushMessage = new PushMessageRequest(source.userId(), List.of(new TextMessage.Builder(((TextMessageContent) messageContent).text()).build()), false, null);
//            lineMessagingClient.pushMessage(channelId, pushMessage);
        }
    }

    @SuppressWarnings("unused")
	private MessageContent getMessageContent(JsonNode event) throws JsonProcessingException {
        ObjectMapper om = getObjectMapper();
        return om.treeToValue(event.get("message"), MessageContent.class);
    }

    @SuppressWarnings("unused")
	private Source getSource(JsonNode event) throws JsonProcessingException {
        ObjectMapper om = getObjectMapper();
        return om.treeToValue(event.get("source"), Source.class);
    }

    private boolean validateSignature(HttpHeaders headers, String body, String channelKey) throws Exception {
        ChatChannelConfig lineChannelConfig = lineMessagingService.getLineChannelConfig(channelKey);
        SecretKeySpec key = new SecretKeySpec(lineChannelConfig.getChannelSecret().getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] source = body.getBytes(StandardCharsets.UTF_8);
        String signature = Base64.encodeBase64String(mac.doFinal(source));
        String headerSignature = Objects.requireNonNull(headers.get("x-line-signature")).stream().findFirst().orElseThrow(() -> new RuntimeException("Missing signature"));
        return signature.equals(headerSignature);
    }

    private Long getChannelId(String channelKey) throws ExecutionException {
        return lineMessagingService.getLineChannelConfig(channelKey).getChannelId();
    }

    private ChatUser saveChatUser(String channelId, UserProfileResponse userProfileResponse) throws ExecutionException {
        ChatUser chatUser = new ChatUser();
        chatUser.setChannelId(lineMessagingService.getLineChannelConfig(channelId).getChannelId());
        chatUser.setUserId(userProfileResponse.userId());
        chatUser.setDisplayName(userProfileResponse.displayName());
        chatUser.setPictureUrl(userProfileResponse.pictureUrl().toString());
        chatUser.setStatusMessage(userProfileResponse.statusMessage());
        chatService.saveChatUser(chatUser);
        return chatUser;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

}
