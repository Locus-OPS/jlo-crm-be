package th.co.locus.jlo.external.api.bean;

import lombok.Data;

@Data
public class ApiServiceResponse<T> {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";

	private Object data;
	private String status;
	private Integer errorCode;
	private String message;

	public static <T> ApiServiceResponse<T> success(T object) {
		ApiServiceResponse<T> resp = new ApiServiceResponse<T>();
		resp.setData(object);
		resp.setStatus(SUCCESS);
		return resp;
	}
	
	public static <T> ApiServiceResponse<T> success(T object , String msg ) {
		ApiServiceResponse<T> resp = new ApiServiceResponse<T>();
		resp.setData(object);
		resp.setStatus(SUCCESS);
		resp.setMessage(msg);
		return resp;
	}
	

	public static <T> ApiServiceResponse<T> fail() {
		return fail(500, "");
	}

	public static <T> ApiServiceResponse<T> fail(String message) {
		return fail(500, message);
	}

	public static <T> ApiServiceResponse<T> fail(Integer errorCode, String message) {
		ApiServiceResponse<T> resp = new ApiServiceResponse<T>();
		resp.setData(null);
		resp.setErrorCode(errorCode);
		resp.setMessage(message);
		resp.setStatus(FAIL);
		return resp;
	}

	public static <T> ApiServiceResponse<T> setBadRequestResponseError(String errorCode, String errorMsg) {
		ApiServiceResponse<T> resp = new ApiServiceResponse<T>();
		resp.setErrorCode(400);
		resp.setMessage("Bad Request Response");
		resp.setStatus(FAIL);
		ExceptionData exceptionData = new ExceptionData(errorCode, errorMsg);
		resp.setData(exceptionData);
		return resp;
	}

}
