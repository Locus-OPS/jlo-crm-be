package th.co.locus.jlo.config.security;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ActiveDirectoryAuthentication implements Authentication {

	@Autowired
	private UserService userService;

	@Value("${ldap.host}")
	String ldapHost;

	@Value("${ldap.domain}")
	String ldapDomain;

	@Value("${ldap.dn}")
	String ldapDN;
 
	@Value("${key.jlo.secret}")
	String keySecret;

	@Override
	public boolean authenticate(String username, String password, SecurityUserDTO userLogin) throws AuthenticationException {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String ipAddress = CommonUtil.getIpAddress(request);

		HashMap<String, String> map = new HashMap<>();
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, ldapHost);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, ldapDomain + username); // we have 2 \\ because it's a escape char
			env.put(Context.SECURITY_CREDENTIALS, password);

			InitialDirContext ctx = new InitialDirContext(env);
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setReturningAttributes(new String[] { "displayName", "givenName", "sn", "mail", "memberOf" });
			NamingEnumeration<SearchResult> res = ctx.search(ldapDN, "sAMAccountName=" + username + "", searchControls);

			while (res.hasMoreElements()) {
				SearchResult result = res.nextElement();
				Attributes atts = result.getAttributes();
				NamingEnumeration<String> dis = atts.getIDs();
				while (dis.hasMoreElements()) {
					String string = (String) dis.nextElement();
					log.info(string + " = " + atts.get(string).get().toString());
					map.put(string, atts.get(string).get().toString());
				}
			}

			boolean result = ctx != null;
			if (result) {
				log.info(" AD Checked Pass!");
				UserLoginAttemptModelBean obj = new UserLoginAttemptModelBean();
				obj.setUserId(username);
				obj.setType("Login");
				obj.setStatusCode("200");
				obj.setStatusMessage("Success");
				obj.setIpAddress(ipAddress);
				userService.insertUserLoginAttempt(obj);
			}

			if (ctx != null)
				ctx.close();

//			return map;
			return true;
		} catch (NamingException ex) {
			log.error(ex.getMessage(), ex);
			
			UserLoginAttemptModelBean obj = new UserLoginAttemptModelBean();
			obj.setUserId(username);
			obj.setType("Login");
			obj.setStatusCode(getAuthenticatedExceptionCode(ex.getExplanation()));
			obj.setStatusMessage(getAuthenticatedExceptionDetail(ex.getExplanation()));
			obj.setIpAddress(ipAddress);
			userService.insertUserLoginAttempt(obj);

			throw new AuthenticationServiceException(getAuthenticatedExceptionDetail(ex.getExplanation()));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			UserLoginAttemptModelBean obj = new UserLoginAttemptModelBean();
			obj.setUserId(username);
			obj.setType("Login");
			obj.setStatusCode("998");
			obj.setStatusMessage("Can't login using Active Directory, Exception logic process");
			obj.setIpAddress(ipAddress);
			userService.insertUserLoginAttempt(obj);
			
			throw new AuthenticationServiceException("Can't login using Active Directory, Exception logic process");
		}
	}

	private String getAuthenticatedExceptionDetail(String errorCode) {

		if (errorCode.indexOf("data 525") != -1) {
			return "The specified account does not exist.";
		} else if (errorCode.indexOf("data 52e") != -1) {
			return "Unknown user name or bad password.";
		} else if (errorCode.indexOf("data 530") != -1) {
			return "Account logon time restriction violation.";
		} else if (errorCode.indexOf("data 531") != -1) {
			return "User not allowed to log on to this system.";
		} else if (errorCode.indexOf("data 532") != -1) {
			return "Password expired.";
		} else if (errorCode.indexOf("data 533") != -1) {
			return "Account currently disabled.";
		} else if (errorCode.indexOf("data 701") != -1) {
			return "Account expired.";
		} else if (errorCode.indexOf("data 773") != -1) {
			return "User must reset password.";
		} else if (errorCode.indexOf("data 775") != -1) {
			return "Account locked out.";
		} else {
			return "Unexpected error from active directory : " + errorCode;
		}
	}

	private String getAuthenticatedExceptionCode(String errorCode) {
		if (errorCode.indexOf("data 525") != -1) {
			return "525";
		} else if (errorCode.indexOf("data 52e") != -1) {
			return "52e";
		} else if (errorCode.indexOf("data 530") != -1) {
			return "530";
		} else if (errorCode.indexOf("data 531") != -1) {
			return "531";
		} else if (errorCode.indexOf("data 532") != -1) {
			return "532";
		} else if (errorCode.indexOf("data 533") != -1) {
			return "533";
		} else if (errorCode.indexOf("data 701") != -1) {
			return "701";
		} else if (errorCode.indexOf("data 773") != -1) {
			return "773";
		} else if (errorCode.indexOf("data 775") != -1) {
			return "775";
		} else {
			return "999";
		}
	}

}
