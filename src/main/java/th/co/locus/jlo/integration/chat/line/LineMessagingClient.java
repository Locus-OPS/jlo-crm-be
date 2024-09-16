package th.co.locus.jlo.integration.chat.line;

import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class LineMessagingClient {

    @Value("${line.endpoint.profile}")
    private String lineEndpointProfile;

    @Value("${line.endpoint.message-reply}")
    private String lineEndpointMessageReply;

    @Value("${line.endpoint.message-push}")
    private String lineEndpointMessagePush;

    @Autowired
    private LineMessagingService lineMessagingService;

    public CompletableFuture<Result<UserProfileResponse>> getProfile(String channelId, String uuid) throws ExecutionException {
        return MessagingApiClient.builder(getChannelToken(channelId))
                .build()
                .getProfile(uuid)
                .whenComplete((profile, throwable) -> {
                    if (throwable != null) {
                        log.error(throwable.toString());
                    } else {
                        System.out.println(profile);
                    }
                });
    }

    public CompletableFuture<Result<ReplyMessageResponse>> replyMessage(String channelId, ReplyMessageRequest replyMessageRequest) throws ExecutionException {
        return MessagingApiClient.builder(getChannelToken(channelId)).build().replyMessage(replyMessageRequest);
    }

    public CompletableFuture<Result<PushMessageResponse>> pushMessage(String channelId, PushMessageRequest pushMessageRequest) throws ExecutionException {
        return MessagingApiClient.builder(getChannelToken(channelId)).build().pushMessage(null, pushMessageRequest);
    }

    private String getChannelToken(String channelId) throws ExecutionException {
        return lineMessagingService.getLineChannelConfig(channelId).getChannelToken();
    }

}
