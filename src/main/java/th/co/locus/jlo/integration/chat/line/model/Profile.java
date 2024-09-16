package th.co.locus.jlo.integration.chat.line.model;

import lombok.Data;

@Data
public class Profile {
    private String displayName;
    private String userId;
    private String language;
    private String pictureUrl;
    private String statusMessage;
}
