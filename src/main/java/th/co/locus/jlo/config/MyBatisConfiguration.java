package th.co.locus.jlo.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import com.github.pagehelper.PageInterceptor;

import th.co.locus.jlo.jdbc.CommonDaoImpl;
import th.co.locus.jlo.jdbc.reloader.spring.factory.MybatisXmlMapperAutoReLoaderFactoryBean;

@Configuration
public class MyBatisConfiguration {

	public static final String MYBATIS_CONFIG_BEAN_NAME = "JLOMyBatisConfiguration";
	public static final String MAPPER_LOCATIONS_BEAN_NAME = "JLOMapperLocations";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name = MYBATIS_CONFIG_BEAN_NAME)
	@ConditionalOnMissingBean(name = MYBATIS_CONFIG_BEAN_NAME)
	public Resource myBatisConfigLocation() {
		return applicationContext.getResource("classpath:mapper/mybatis-config.xml");
	}

	@Bean(name = MAPPER_LOCATIONS_BEAN_NAME)
	@ConditionalOnMissingBean(name = MAPPER_LOCATIONS_BEAN_NAME)
	public Resource[] mapperLocations() throws Exception {
		return applicationContext.getResources("classpath:th/co/locus/jlo/**/mapper/*-mapper.xml");
	}

	@Bean
	@Profile("dev")
	public MybatisXmlMapperAutoReLoaderFactoryBean configMybatisXmlMapperAutoReloader() throws Exception {
		MybatisXmlMapperAutoReLoaderFactoryBean factoryBean = new MybatisXmlMapperAutoReLoaderFactoryBean();
		factoryBean.setSqlSessionFactory(sqlSessionFactory());
		return factoryBean;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(myBatisConfigLocation());
		factoryBean.setMapperLocations(mapperLocations());
		PageInterceptor pageHelper = new PageInterceptor();
		factoryBean.setPlugins(new Interceptor[] { pageHelper });
		return factoryBean.getObject();
	}

	@Bean
	public CommonDaoImpl commonDao() throws Exception {
		return new CommonDaoImpl(sqlSessionFactory(), mapperLocations());
	}

}
