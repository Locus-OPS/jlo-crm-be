package th.co.locus.jlo.system.email;

import th.co.locus.jlo.common.ServiceResult;

public interface EmailService {

	public ServiceResult<Boolean> insertReadLog(String refName, String email);
	
}
