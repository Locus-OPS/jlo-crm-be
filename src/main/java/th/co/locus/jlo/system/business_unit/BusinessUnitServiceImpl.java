package th.co.locus.jlo.system.business_unit;

import org.springframework.stereotype.Service;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.business_unit.bean.BusinessUnitModelBean;
import th.co.locus.jlo.system.business_unit.bean.SearchBusinessUnitModelBean;

@Service
public class BusinessUnitServiceImpl extends BaseService implements BusinessUnitService {

	@Override
	public ServiceResult<Page<BusinessUnitModelBean>> getBusinessUnitList(SearchBusinessUnitModelBean bean,
																		  PageRequest pageRequest) {
		return success(commonDao.selectPage("business-unit.getBusinessUnitList", bean, pageRequest));
	}

	@Override
	public ServiceResult<BusinessUnitModelBean> createBusinessUnit(BusinessUnitModelBean bean) {
		int result = commonDao.update("business-unit.createBusinessUnit", bean);
		if (result > 0) {
			return success(commonDao.selectOne("business-unit.findBusinessUnitById", bean));
		}
		return fail(); 
	}

	@Override
	public ServiceResult<BusinessUnitModelBean> updateBusinessUnit(BusinessUnitModelBean bean) {
		int result = commonDao.update("business-unit.updateBusinessUnit", bean);
		if (result > 0) {
			return success(commonDao.selectOne("business-unit.findBusinessUnitById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteBusinessUnit(BusinessUnitModelBean bean) {
		return success(commonDao.delete("business-unit.deleteBusinessUnit", bean));
	}
}
