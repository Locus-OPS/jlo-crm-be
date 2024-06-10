package th.co.locus.jlo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityUserDetailsService securityUserDetailsService;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override // Swagger Config
    public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
			"/swagger-resources", "/configuration/security",
            "/swagger-ui.html", "/webjars/**","/swagger/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(securityUserDetailsService);
    }
	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/assets/**").permitAll()
//                .antMatchers("/api/**").permitAll() //to allow API request from Angular
//                .antMatchers("/tpl/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/error/**").permitAll()
//                .antMatchers("/login*").permitAll()
//                .antMatchers("/expired*").permitAll()
//                .antMatchers("/service/**").permitAll()
//                .antMatchers("/service-system/**").permitAll()
//                .antMatchers("/service-rest/**").permitAll()
//                .antMatchers("/searchUserByIdAndEmail").permitAll()
//                
//            .anyRequest()
//                .authenticated()
//            .and()
//                .csrf() 
//                    .disable() //cross site script disable
//            .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/login-process")
//                .successHandler(new SuccessLoginHandler())
//                .failureHandler(new FailureLoginHandler())
//                .permitAll()
//            .and()
//                .logout()
//                .logoutUrl("/logout")
//                .permitAll()
//            .and()
//                .httpBasic()
//            .and()
//                .sessionManagement()
//                    .maximumSessions(10) // user นั้น login ได้กี่คน
//                    .maxSessionsPreventsLogin(false) // false เตะคนก่อนออก
//                    .expiredUrl("/expired")
//                .sessionRegistry(sessionRegistry());
//    }
    
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
//    }
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        SessionRegistry sessionRegistry = new SessionRegistryImpl();
//        return sessionRegistry;
//    }
}
