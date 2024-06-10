package th.co.locus.jlo.jdbc.reloader.spring.factory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import th.co.locus.jlo.jdbc.reloader.xml.mapper.MybatisXmlMapperReloaderFactory;

public class MybatisXmlMapperAutoReLoaderFactoryBean implements FactoryBean<MybatisXmlMapperReloaderFactory>, InitializingBean, DisposableBean {

    private SqlSessionFactory sqlSessionFactory;

    private MybatisXmlMapperReloaderFactory mybatisXmlMapperAutoReLoader;

    @Override
    public MybatisXmlMapperReloaderFactory getObject() throws Exception {
        if (this.mybatisXmlMapperAutoReLoader == null) {
            afterPropertiesSet();
        }
        return this.mybatisXmlMapperAutoReLoader;
    }

    @Override
    public void destroy() throws Exception {
        if (this.mybatisXmlMapperAutoReLoader != null) {
            this.mybatisXmlMapperAutoReLoader.destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.mybatisXmlMapperAutoReLoader = new MybatisXmlMapperReloaderFactory();
        this.mybatisXmlMapperAutoReLoader.setSqlSessionFactory(this.sqlSessionFactory);
        this.mybatisXmlMapperAutoReLoader.init();
    }

    @Override
    public Class<?> getObjectType() {
        return MybatisXmlMapperReloaderFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

}
