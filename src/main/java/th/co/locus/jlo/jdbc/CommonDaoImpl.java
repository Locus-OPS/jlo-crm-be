package th.co.locus.jlo.jdbc;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageRowBounds;

import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;

@Component
public class CommonDaoImpl extends SqlSessionTemplate implements CommonDao {

	public CommonDaoImpl(SqlSessionFactory sqlSessionFactory, Resource[] mapperLocations) {
		super(sqlSessionFactory);
	}

	@Override
	public <T> Page<T> selectPage(String sqlId, Object param, PageRequest pageRequest) {
		PageRowBounds rowBounds = new PageRowBounds(pageRequest.getOffset(), pageRequest.getPageSize());
		List<T> resultList = this.selectList(sqlId, param, rowBounds);
		return new Page<>(resultList, pageRequest, rowBounds.getTotal());
	}

}
