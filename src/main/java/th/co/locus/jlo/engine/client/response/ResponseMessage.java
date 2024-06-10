package th.co.locus.jlo.engine.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseMessage<T>  {

	private Status status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private T data;
	
	public boolean isSuccess() {
		return status != null && "S".equals(status.getStatusCode());
	}
	
}
