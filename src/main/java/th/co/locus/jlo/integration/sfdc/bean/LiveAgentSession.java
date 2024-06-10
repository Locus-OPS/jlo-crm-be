package th.co.locus.jlo.integration.sfdc.bean;

import lombok.Data;

@Data
public class LiveAgentSession {

	private String key;
	private String id;
	private String affinityToken;
	private int clientPollTimeout;
	
}
