package th.co.locus.jlo.integration.sfdc.bean;

import lombok.Data;

@Data
public class ChatRequest {

	private String organizationId;
	private String deploymentId;
	private String buttonId;
	private String sessionId;
	private String userAgent;
	private String language;
	private String screenResolution;
	private String visitorName;
	private PrechatDetail[] prechatDetails;
	private PrechatEntity[] prechatEntities;
	private boolean receiveQueueUpdates;
	private boolean isPost;
	
	public boolean getIsPost() {
		return this.isPost;
	}
	
}
