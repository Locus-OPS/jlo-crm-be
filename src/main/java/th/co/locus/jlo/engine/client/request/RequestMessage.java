package th.co.locus.jlo.engine.client.request;

import lombok.Data;

@Data
public class RequestMessage<T> {

	private T data;
}
