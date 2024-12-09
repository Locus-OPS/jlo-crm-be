package th.co.locus.jlo.exception;

import java.util.List;

import th.co.locus.jlo.common.bean.ServiceResult;

import th.co.locus.jlo.exception.bean.ExceptionModelBean;

public interface ExceptionService {

	public ServiceResult<List<ExceptionModelBean>> searchExceptionList(String module);
}
