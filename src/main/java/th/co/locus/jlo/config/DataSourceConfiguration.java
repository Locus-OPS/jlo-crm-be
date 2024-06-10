package th.co.locus.jlo.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfiguration {

	@Bean(name = "dataSource") 
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSource() {
		log.info("Initial Datasource Configuration");
		log.info("***********************************************");
		try {
			// fix for jboss
			return (BasicDataSource) DataSourceBuilder.create().build();
		} catch (Exception e) {
			// fix for tomcat
			return DataSourceBuilder.create().build();
		}
	}

}
