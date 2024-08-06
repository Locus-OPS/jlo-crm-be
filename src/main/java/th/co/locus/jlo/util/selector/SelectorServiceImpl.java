package th.co.locus.jlo.util.selector;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.common.util.selector.bean.SelectorModelBean;

@Service
public class SelectorServiceImpl extends BaseService implements SelectorService {

	private static int DEFAULT_MINUTE = 10;
	private static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;

	LoadingCache<String, List<SelectorModelBean>> getProgramCache;

	@PostConstruct
	public void init() {
		getProgramCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getProgram");
						return resultList;
					}
				});
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getProgram() {
		try {
			return success(getProgramCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getProvince(String parent) {
		List<SelectorModelBean> resultList;
		resultList = commonDao.selectList("selector.getProvince", Map.of("parent", parent));
		return success(resultList);
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getDistrict(String parent) {
		List<SelectorModelBean> resultList;
		resultList = commonDao.selectList("selector.getDistrict", Map.of("parent", parent));
		return success(resultList);
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getSubDistrict(String parent) {
		List<SelectorModelBean> resultList;
		resultList = commonDao.selectList("selector.getSubDistrict", Map.of("parent", parent));
		return success(resultList);
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getPostCode(String parent) {
		List<SelectorModelBean> resultList;
		resultList = commonDao.selectList("selector.getPostCode", Map.of("parent", parent));
		return success(resultList);
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getPostCodeDetail(String parent) {
		List<SelectorModelBean> resultList;
		resultList = commonDao.selectList("selector.getPostCodeDetail", Map.of("parent", parent));
		return success(resultList);
	}

}
