package th.co.locus.jlo.integration.chat.bean;

import lombok.Data;

@Data
public class ChatChannelConfig {
    private Long channelId;
    private String channelSecret;
    private String channelToken;
    private String channelKey;
    private String channelType;
}
