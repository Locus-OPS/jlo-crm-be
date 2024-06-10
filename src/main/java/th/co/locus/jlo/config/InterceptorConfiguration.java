package th.co.locus.jlo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * JLO previous version used WebMvcConfigurerAdapter. WebMvcConfigurerAdapter is
 * deprecated as WebMvcConfigurer has default methods (since Java 8).
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
	}

	/**
	 * To set up the "default / welcome page" in Spring Boot
	 * To map "/" with "/index.jsp"
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/index.jsp");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

}
