package th.co.locus.jlo.integration.sfdc.bean;

import java.util.List;

import lombok.Data;

@Data
public class MessageResponse {

	private List<Message> messages;
	
}
