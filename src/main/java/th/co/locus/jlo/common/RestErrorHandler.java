package th.co.locus.jlo.common;

import java.net.SocketTimeoutException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.apache.ibatis.executor.ExecutorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import th.co.locus.jlo.common.exception.BusinessRuntimeException;

@ControllerAdvice
public class RestErrorHandler {

	@SuppressWarnings({ "rawtypes" })
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ApiResponse processValidationError(MethodArgumentNotValidException ex) {
		ApiResponse response = new ApiResponse();
		response.setStatus(Boolean.FALSE);
		response.setErrorCode("400");
		response.setMessage(extractErrors(ex.getBindingResult().getFieldErrors()));
		return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity processAccessDenied(AccessDeniedException ex) {
		ApiResponse response = new ApiResponse();
		response.setStatus(Boolean.FALSE);
		response.setErrorCode("401");
		response.setMessage("Access Denied");
		return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(SocketTimeoutException.class)
	@ResponseBody
	public ApiResponse processValidationError(SocketTimeoutException ex) {
		ApiResponse response = new ApiResponse();
		response.setStatus(Boolean.FALSE);
		response.setErrorCode("400");
		response.setMessage("Connection Timeout");
		return response;
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(BusinessRuntimeException.class)
	@ResponseBody
	private ApiResponse processValidationError(BusinessRuntimeException ex) {
		ex.printStackTrace();
		return ApiResponse.fail("1001", ex.getMessage());
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ApiResponse processValidationError(Exception ex) {
		ex.printStackTrace();
		if (ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
			return handleSQLIntegrityConstraintViolationException(ex.getCause());
		} else if (ex.getCause() instanceof ExecutorException) {
			ApiResponse response = new ApiResponse();
			response.setStatus(Boolean.FALSE);
			response.setErrorCode("400");
			response.setMessage(ex.getMessage());
			return response;
		} 
//		else if (ex.getCause() instanceof MySQLSyntaxErrorException) {
//			ApiResponse response = new ApiResponse();
//			response.setStatus(Boolean.FALSE);
//			response.setErrorCode("400");
//			response.setMessage(ex.getMessage());
//			return response;
//		} 
		else {
			ApiResponse response = new ApiResponse();
			response.setStatus(Boolean.FALSE);
			response.setErrorCode("400");
			response.setMessage("Uncaught Exception : Please contact system administrator.");
			return response;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ApiResponse handleSQLIntegrityConstraintViolationException(Throwable ex) {
		ApiResponse response = new ApiResponse();
		response.setStatus(Boolean.FALSE);
		response.setErrorCode("400");
		response.setMessage("DUPLICATE");
		return response;
	}

	private String extractErrors(List<FieldError> errors) {
		StringBuilder sb = new StringBuilder();
		for (FieldError error : errors) {
			if (sb.length() > 0) {
				sb.append(" , ");
			}
			sb.append(String.format("[%s] %s", error.getField(), error.getDefaultMessage()));
		}
		return sb.toString();
	}
}
