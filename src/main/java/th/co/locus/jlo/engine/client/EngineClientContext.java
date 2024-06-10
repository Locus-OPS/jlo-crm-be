package th.co.locus.jlo.engine.client;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

public class EngineClientContext {

	private static String _accessTokenUrl = "/oauth/token";
	private static String _grantType = "client_credentials";
	private static String _clientId = null;
	private static String _clientSecret = null;

	private static DefaultOAuth2ClientContext context = new DefaultOAuth2ClientContext();
	private static OAuth2RestTemplate restTemplate;
	
	public static void init(String serverUrl, String clientId, String clientSecret) {
		_accessTokenUrl = serverUrl + _accessTokenUrl;
		_clientId = clientId;
		_clientSecret = clientSecret;
	}

	public static DefaultOAuth2ClientContext getContext() {
		return context;
	}

	public static ClientCredentialsResourceDetails getResource() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(_accessTokenUrl);
		resource.setGrantType(_grantType);
		resource.setClientSecret(_clientSecret);
		resource.setClientId(_clientId);
		return resource;
	}

	public static OAuth2RestTemplate getTemplate() {
		if (restTemplate == null) {
			restTemplate = new OAuth2RestTemplate(EngineClientContext.getResource(), EngineClientContext.getContext());
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
			// rf.setReadTimeout(60 * 1000);
			// rf.setConnectTimeout(60 * 1000);
			rf.setOutputStreaming(false);
			restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(rf));
		}
		return restTemplate;
	}

	
}
