package th.co.locus.jlo.engine.client.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Status {

	private String statusCode;
	private String errorCode;
	private String errorMessage;
	
}
