package th.co.locus.jlo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "th.co.locus.jlo")
@SpringBootApplication
public class Jloxg2Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Jloxg2Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Jloxg2Application.class, args);
	}

}
