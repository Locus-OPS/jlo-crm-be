package th.co.locus.jlo.common;

import lombok.Data;

@Data
public class ApiRequest<T> {

	private T data;
	
}
