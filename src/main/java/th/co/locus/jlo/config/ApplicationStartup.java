package th.co.locus.jlo.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
	
    @Override
    public void onApplicationEvent(ApplicationReadyEvent e) {
    	log.info("ApplicationReadyEvent : onApplicationEvent");
    	startAsync();
    }
    
    @Async
    private void startAsync() {
    	log.info("ApplicationReadyEvent : startAsync");
//    	CodebookUtils.resetCodeBookList();
//    	CodebookUtils.resetUserList();
    }
    
    /* after upgrading to java 11 
	 * 
	 * Embedded Tomcat has an issue with JAXB jar scan
	 * This is implemented after searching the solution
	 * Here is the error message that was caused during the start up.
	 * 
	 * 2019-07-10 09:37:38.973[WARN ]o.a.t.util.scan.StandardJarScanner 
	 * - Failed to scan [file:/C:/Users/jason/.m2/repository/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-api.jar] 
	 * from classloader hierarchy 
	 * 
	 * */
	@Bean 
	public TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
			}
		};
	}

}
