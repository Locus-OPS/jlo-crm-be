package th.co.locus.jlo.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.user.UserService;
import th.co.locus.jlo.system.user.bean.UserLoginAttemptModelBean;

@Slf4j
@Component
public class UserPasswordAuthentication implements Authentication {
	
	@Autowired
	private UserService userService;

	@Override
	public boolean authenticate(String username, String password, SecurityUserDTO userLogin) throws AuthenticationException {
		log.info("	Pass Input : " + password);
		log.info("	Pass Database : " + userLogin.getPassword());
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String ipAddress = CommonUtil.getIpAddress(request);
		
		if (!password.equals(userLogin.getPassword())) {			
			UserLoginAttemptModelBean obj = new UserLoginAttemptModelBean();
			obj.setUserId(username);
			obj.setType("Login");
			obj.setStatusCode("999");
			obj.setStatusMessage("Password didn't match, Please try again.");
			obj.setIpAddress(ipAddress);
			userService.insertUserLoginAttempt(obj);
			throw new AuthenticationServiceException("Password didn't match, Please try again.");
		}
		
		UserLoginAttemptModelBean obj = new UserLoginAttemptModelBean();
		obj.setUserId(username);
		obj.setType("Login");
		obj.setStatusCode("200");
		obj.setStatusMessage("Success");
		obj.setIpAddress(ipAddress);
		userService.insertUserLoginAttempt(obj);
		
		log.info(" Password Checked Pass!");
		return true;
	}

}
