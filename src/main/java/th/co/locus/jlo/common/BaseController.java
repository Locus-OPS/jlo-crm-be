package th.co.locus.jlo.common;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;

import th.co.locus.jlo.config.security.oauth.CustomTokenEnhancer;

public class BaseController {

	protected String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	@SuppressWarnings("unchecked")
	protected Integer getBuId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
		Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		Map<String, Object> profiles = (Map<String, Object>) details.get(CustomTokenEnhancer.KEY_PROFILE);
		String buId = (String) profiles.get("buId");
		if (StringUtils.isEmpty(buId)) {
			return null;
		} else {
			return Integer.parseInt(buId);			
		}
	}
	
	@SuppressWarnings("unchecked")
	protected String getRoleCode() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
		Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		Map<String, Object> profiles = (Map<String, Object>) details.get(CustomTokenEnhancer.KEY_PROFILE);
		String roleCode = (String) profiles.get("roleCode");
		if (StringUtils.isEmpty(roleCode)) {
			return null;
		} else {
			return roleCode;			
		}
	}
	
	protected <T> PageRequest getPageRequest(ApiPageRequest<T> req) {
		return new PageRequest(req.getPageNo(), req.getPageSize());
	}

}
