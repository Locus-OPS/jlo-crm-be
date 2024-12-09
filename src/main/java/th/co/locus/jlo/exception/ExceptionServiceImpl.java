package th.co.locus.jlo.exception;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.exception.bean.ExceptionModelBean;

@Service
@Slf4j
public class ExceptionServiceImpl extends BaseService implements ExceptionService {

	private static int DEFAULT_TIME = 30;
	private static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;

	private LoadingCache<String, List<ExceptionModelBean>> getExceptionListCache;

	@PostConstruct
	public void init() {
		getExceptionListCache = CacheBuilder.newBuilder().maximumSize(20).expireAfterWrite(DEFAULT_TIME, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<ExceptionModelBean>>() {
					public List<ExceptionModelBean> load(String serviceName) {
						return commonDao.selectList("exception.searchExceptionList",
								Map.of("serviceName", serviceName));
					}
				});
	}

	@Override
	public ServiceResult<List<ExceptionModelBean>> searchExceptionList(String serviceName) {
		try {
			return success(getExceptionListCache.get(serviceName));
		} catch (ExecutionException e) {
			log.error(e.getMessage(), e);
			return fail();
		}
	}
}