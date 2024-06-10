package th.co.locus.jlo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiPageResponse<T> extends ApiResponse<T> {
	
	private long total;
	
	public static <T> ApiPageResponse<T> success(T object, long total) {
		ApiPageResponse<T> resp = new ApiPageResponse<T>();
		resp.setData(object);
		resp.setTotal(total);
		resp.setStatus(Boolean.TRUE);
		return resp;
	}
	
	public static <T> ApiPageResponse<T> fail() {
		return fail("500", "");
	}
	
	public static <T> ApiPageResponse<T> fail(String message) {
		return fail("500", message);
	}
	
	public static <T> ApiPageResponse<T> fail(String errorCode, String message) {
		ApiPageResponse<T> resp = new ApiPageResponse<T>();
		resp.setData(null);
		resp.setErrorCode(errorCode);
		resp.setMessage(message);
		resp.setStatus(Boolean.FALSE);
		return resp;
	}

}
