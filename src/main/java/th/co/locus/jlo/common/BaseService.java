package th.co.locus.jlo.common;

import org.springframework.beans.factory.annotation.Autowired;

import th.co.locus.jlo.jdbc.CommonDao;

public class BaseService{

	@Autowired
	protected CommonDao commonDao;

	protected <T> ServiceResult<T> success(T object) {
		return new ServiceResult<T>(object);
	}

	protected <T> ServiceResult<T> fail() {
		return fail("", "");
	}
	
	protected <T> ServiceResult<T> fail(String responseCode , String responseDescription) {
		
		ServiceResult<T> x = new ServiceResult<T>(Boolean.FALSE);
		x.setResponseCode(responseCode);
		x.setResponseDescription(responseDescription);
		
		return x;
	}
	
	
	

}
