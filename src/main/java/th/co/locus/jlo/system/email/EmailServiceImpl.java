package th.co.locus.jlo.system.email;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class EmailServiceImpl extends BaseService implements EmailService {

	@Override
	public ServiceResult<Boolean> insertReadLog(String refName, String email) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			Map<String, String> param = new HashMap<String, String>();
			param.put("refName", refName);
			param.put("email", email);
			param.put("ipAddress", request.getRemoteAddr());
			param.put("xForwardedFor", request.getHeader("X-Forwarded-For"));
			commonDao.insert("email.insertReadLog", param);
		} catch (Exception e) {
			
		}
		return success(Boolean.TRUE);
	}

}
