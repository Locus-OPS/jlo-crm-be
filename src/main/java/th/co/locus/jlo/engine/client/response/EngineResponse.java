package th.co.locus.jlo.engine.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class EngineResponse<T> {

	private Status status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private T data;
	
	public boolean isSuccess() {
		return status != null && "S".equals(status.getStatusCode());
	}
}
