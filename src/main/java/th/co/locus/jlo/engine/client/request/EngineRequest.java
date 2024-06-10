package th.co.locus.jlo.engine.client.request;

import lombok.Data;

@Data
public class EngineRequest<T> {

	private T data;
	
}
