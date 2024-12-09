package th.co.locus.jlo.exception;

import java.util.List;

import org.springframework.util.CollectionUtils;

import th.co.locus.jlo.exception.bean.ExceptionModelBean;

public class ExceptionUtils {
	public static String getExceptionMessage(String errorCode, List<ExceptionModelBean> exceptionList) {
		if (CollectionUtils.isEmpty(exceptionList)) {
			return null;
		}
		ExceptionModelBean exception = exceptionList.stream()
				.filter(item -> errorCode.equals(item.getErrorCode()))
				.findAny()
				.orElse(null);
		return exception != null ? exception.getErrorMsg() : null;
	}
}
