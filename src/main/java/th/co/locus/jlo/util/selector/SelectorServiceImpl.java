package th.co.locus.jlo.util.selector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.selector.bean.SelectorCustomModelBean;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

@Service
public class SelectorServiceImpl extends BaseService implements SelectorService {
	
	private static int DEFAULT_MINUTE = 10;
	private static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;
	
	LoadingCache<String, List<SelectorModelBean>> getCodebookByTypeCache;
	LoadingCache<String, List<SelectorModelBean>> getBusinessUnitCache;
	LoadingCache<String, List<SelectorModelBean>> getRoleCache;
	LoadingCache<String, List<SelectorModelBean>> getPositionCache;
	LoadingCache<String, List<SelectorModelBean>> getCodebookTypeCache;
	LoadingCache<String, List<SelectorModelBean>> getProgramCache;
	LoadingCache<String, List<SelectorModelBean>> getShopTypeCache;
	LoadingCache<String, List<SelectorModelBean>> getProductCategoryCache;
	LoadingCache<String, List<SelectorModelBean>> getCampaignCache;
	LoadingCache<String, List<SelectorModelBean>> getAttrGroupCache;
	LoadingCache<String, List<SelectorModelBean>> getAttrCache;
	LoadingCache<String, List<SelectorModelBean>> getPointTypeCache;
	LoadingCache<String, List<SelectorModelBean>> getPartnerTypeCache;
	LoadingCache<String, List<SelectorModelBean>> getParentMenuCache;
	LoadingCache<String, List<SelectorCustomModelBean>> getSaleProductCategoryCache;
	LoadingCache<String, List<SelectorModelBean>> getCodebookByCodeTypeAndParentIdCache;
	
	@PostConstruct
	public void init() {
		getCodebookByTypeCache = CacheBuilder.newBuilder().maximumSize(20).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {

						List<SelectorModelBean> resultList;
						resultList = commonDao.selectList("selector.getCodebookByCodeType", Map.of("codeType", type));
						return resultList;
					}
				});
		getCodebookByCodeTypeAndParentIdCache = CacheBuilder.newBuilder().maximumSize(20).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						String data[] = type.split(",");
						List<SelectorModelBean> resultList;
						resultList = commonDao.selectList("selector.getCodebookByCodeTypeAndParentId", Map.of("codeType", data[0], "parentId", data[1]));
						return resultList;
					}
				});
		getBusinessUnitCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getBusinessUnit");
						return resultList;
					}
				});
		getRoleCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getRole");
						return resultList;
					}
				});
		getPositionCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getPosition");
						return resultList;
					}
				});
		getCodebookTypeCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getCodebookType");
						return resultList;
					}
				});

		getProgramCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getProgram");
						return resultList;
					}
				});

		getShopTypeCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getShopType");
						return resultList;
					}
				});

		getProductCategoryCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getProductCategory");
						return resultList;
					}
				});
		
		getSaleProductCategoryCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorCustomModelBean>>() {
					public List<SelectorCustomModelBean> load(String type) {
						List<SelectorCustomModelBean> resultList = commonDao.selectList("selector.getSaleProductCategory");
						return resultList;
					}
				});

		getCampaignCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getCampaign");
						return resultList;
					}
				});

		getAttrGroupCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getAttrGroup");
						return resultList;
					}
				});

		getAttrCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getAttr");
						return resultList;
					}
				});

		getPointTypeCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getPointType");
						return resultList;
					}
				});

		getPartnerTypeCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getPartnerType");
						return resultList;
					}
				});
		
		getParentMenuCache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(DEFAULT_MINUTE, DEFAULT_UNIT)
				.build(new CacheLoader<String, List<SelectorModelBean>>() {
					public List<SelectorModelBean> load(String type) {
						List<SelectorModelBean> resultList = commonDao.selectList("selector.getParentMenu");
						return resultList;
					}
				});
	}

	@Override
	public ServiceResult<Map<String, List<SelectorModelBean>>> getMultipleCodebookByCodeType(String[] types) {
		try {
			Map<String, List<SelectorModelBean>> result = new HashMap<String, List<SelectorModelBean>>();
			for (String type : types) {
				result.put(type, getCodebookByTypeCache.get(type));
			}
			return success(result);
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getCodebookByCodeTypeAndParentId(String data) {
		try {
			return success(getCodebookByCodeTypeAndParentIdCache.get(data));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}
	
	@Override
	public ServiceResult<List<SelectorModelBean>> getCodebookByCodeType(String type) {
		try {
			return success(getCodebookByTypeCache.get(type));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getBusinessUnit() {
		try {
			return success(getBusinessUnitCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getRole() {
		try {
			return success(getRoleCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getPosition() {
		try {
			return success(getPositionCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
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
	public ServiceResult<List<SelectorModelBean>> getCodebookType() {
		try {
			return success(getCodebookTypeCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getShopType() {
		try {
			return success(getShopTypeCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getProductCategory() {
		try {
			return success(getProductCategoryCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getCampaign() {
		try {
			return success(getCampaignCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getAttrGroup() {
		try {
			return success(getAttrGroupCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getAttr() {
		try {
			return success(getAttrCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getPointType() {
		try {
			return success(getPointTypeCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getPartnerType() {
		try {
			return success(getPartnerTypeCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getParentMenu() {
		try {
			return success(getParentMenuCache.get(""));
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
	
	@Override
	public void clearBusinessUnitCache() {
		getBusinessUnitCache.invalidateAll();
	}

	@Override
	public void clearPositionCache() {
		getPositionCache.invalidateAll();
	}
	
	@Override
	public void clearParentMenuCache() {
		getParentMenuCache.invalidateAll();
	}

	@Override
	public ServiceResult<List<SelectorCustomModelBean>> getSaleProductCategory() {
		try {
			return success(getSaleProductCategoryCache.get(""));
		} catch (ExecutionException e) {
			e.printStackTrace();
			return fail();
		}
	}
}
