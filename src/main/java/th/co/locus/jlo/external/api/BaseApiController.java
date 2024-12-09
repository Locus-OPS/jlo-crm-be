package th.co.locus.jlo.external.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.exception.ExceptionService;
import th.co.locus.jlo.exception.ExceptionUtils;
import th.co.locus.jlo.exception.bean.ExceptionModelBean;
import th.co.locus.jlo.external.api.bean.ApiServiceResponse;

@Slf4j
public abstract class BaseApiController {

	@Autowired
	private ExceptionService exceptionService;

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ApiServiceResponse<?> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);

		// Load exception list.
		ServiceResult<List<ExceptionModelBean>> getExceptionListResult = exceptionService
				.searchExceptionList("GENERAL_EXCEPTION");
		List<ExceptionModelBean> exceptionList = getExceptionListResult.getResult();

		return ApiServiceResponse.setBadRequestResponseError("I101",
				ExceptionUtils.getExceptionMessage("I101", exceptionList));
	}

}
