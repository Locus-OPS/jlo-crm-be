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
            ,"/workflow-service/v1/**","/jlo-crm-backend/chat/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(JLO_WHITELIST).permitAll()
                        .requestMatchers(String.format("%s/**", lineWebhookPath)).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}