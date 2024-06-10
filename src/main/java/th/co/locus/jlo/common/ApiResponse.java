package th.co.locus.jlo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class ApiResponse<T> {
	
	private boolean status;

	private T data;
	
	private String message;
	
	private String errorCode;
	
	public static <T> ApiResponse<T> success(T object) {
		ApiResponse<T> resp = new ApiResponse<T>();
		resp.setData(object);
		resp.setStatus(Boolean.TRUE);
		return resp;
	}
	
	public static <T> ApiResponse<T> fail() {
		return fail("500", "");
	}
	
	public static <T> ApiResponse<T> fail(String message) {
		return fail("500", message);
	}
	
	public static <T> ApiResponse<T> fail(String errorCode, String message) {
		ApiResponse<T> resp = new ApiResponse<T>();
		resp.setData(null);
		resp.setErrorCode(errorCode);
		resp.setMessage(message);
		resp.setStatus(Boolean.FALSE);
		return resp;
	}
	
}
