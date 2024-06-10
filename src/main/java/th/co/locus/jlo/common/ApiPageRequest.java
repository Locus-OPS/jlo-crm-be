package th.co.locus.jlo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiPageRequest<T> extends ApiRequest<T> {

	private int pageNo = 1;
	private int pageSize = 10;
	
	public ApiPageRequest(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
}
