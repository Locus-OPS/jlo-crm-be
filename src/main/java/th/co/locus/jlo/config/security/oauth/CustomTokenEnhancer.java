package th.co.locus.jlo.config.security.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import th.co.locus.jlo.config.security.SecurityUserDTO;

public class CustomTokenEnhancer implements TokenEnhancer {

	public static final String KEY_PROFILE = "profile";
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put(KEY_PROFILE, (SecurityUserDTO) authentication.getUserAuthentication().getPrincipal());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}