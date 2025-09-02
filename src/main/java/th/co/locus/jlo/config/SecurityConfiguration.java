package th.co.locus.jlo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Value("${line.webhook.path:/api/integration/line/webhook}")
    private String lineWebhookPath;

    private static final String[] AUTH_WHITELIST = {
            "/common/auth/login", "/common/auth/refreshToken"
    };
    private static final String[] SWAGGER_WHITELIST = {
    		 "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-resources"
    };
    private static final String[] JLO_WHITELIST = {
            "/message-channel/**", "/api/landing/**","/api/customer/profile_image/**", "/api/user/profile_image/**" , "/api/email-template/email_template_image/**"
            ,"/workflow-service/v1/**","/chat/**"
    };
    
    private static final String[] HEALTH_CHECK_WHITELIST = {
    		"/actuator/health"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF
				.csrf(csrf -> csrf.disable())

                // Defines authorization for each URL.
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(JLO_WHITELIST).permitAll()
                        .requestMatchers(HEALTH_CHECK_WHITELIST).permitAll()
                        .requestMatchers("%s/**".formatted(lineWebhookPath)).permitAll()
                        .anyRequest().authenticated()
				)

                // Defines a resource server with JWT validation.
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}