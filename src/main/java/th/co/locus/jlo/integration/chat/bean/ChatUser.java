package th.co.locus.jlo.integration.chat.bean;

import lombok.Data;

@Data
public class ChatUser {
    private Long id;
    private Long channelId;
    private String userId;
    private String displayName;
    private String pictureUrl;
    private String statusMessage;
}
