package th.co.locus.jlo.jdbc;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.DisposableBean;

import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;

public interface CommonDao extends SqlSession, DisposableBean {
	
	public <T> Page<T> selectPage(String sqlId, Object param, PageRequest pageRequest);
	
}
