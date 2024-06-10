package th.co.locus.jlo.config.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import th.co.locus.jlo.config.security.oauth.CustomTokenEnhancer;
import th.co.locus.jlo.system.menu.bean.TokenMenuRespModelBean;

public class JLOPermissionEvaluator implements PermissionEvaluator {
	
	public static final String READ = "R";
	public static final String WRITE = "W";
	public static final String EXTRA = "X";

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		return checkPermission(authentication, (String) permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return checkPermission(authentication, (String) permission);
	}

	private boolean checkPermission(Authentication authentication, String permission) {
		List<TokenMenuRespModelBean> respList = getMenuRespList(authentication);
		if (respList == null) {
			return false;
		}
		
		String requestUrl = getApiPath();				

		Optional<TokenMenuRespModelBean> check = respList.stream()
				.filter(item -> item.getApiPath() != null && requestUrl.indexOf(item.getApiPath()) == 0).findAny();
		if (check.isEmpty()) {
			return false;
		} else {
			return isValid(check.get(), permission);
		}
	}

	private boolean isValid(TokenMenuRespModelBean menu, String permission) {
		switch (permission) {
		case READ:
			return menu.getRespFlag() != null && menu.getRespFlag().indexOf(READ) > -1;
		case WRITE:
			return menu.getRespFlag() != null && (menu.getRespFlag().indexOf(WRITE) > -1 || menu.getRespFlag().indexOf(EXTRA) > -1);
		case EXTRA:
			return menu.getRespFlag() != null && menu.getRespFlag().indexOf(EXTRA) > -1;
		default:
			return false;
		}		
	}
	
	private String getApiPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String context = request.getContextPath();
		String path = request.getRequestURI();
		return path.replaceAll(context, "");
	}

	@SuppressWarnings("unchecked")
	private List<TokenMenuRespModelBean> getMenuRespList(Authentication authentication) {
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
		Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		Map<String, Object> profiles = (Map<String, Object>) details.get(CustomTokenEnhancer.KEY_PROFILE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			return Arrays.asList(mapper.readValue(mapper.writeValueAsString(profiles.get("menuRespList")),
					TokenMenuRespModelBean[].class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
