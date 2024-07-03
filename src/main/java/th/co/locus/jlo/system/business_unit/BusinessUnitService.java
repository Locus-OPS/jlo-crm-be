package th.co.locus.jlo.system.business_unit;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.business_unit.bean.BusinessUnitModelBean;
import th.co.locus.jlo.system.business_unit.bean.SearchBusinessUnitModelBean;

public interface BusinessUnitService {

	public ServiceResult<Page<BusinessUnitModelBean>> getBusinessUnitList(SearchBusinessUnitModelBean bean,
																		  PageRequest pageRequest);

	public ServiceResult<BusinessUnitModelBean> createBusinessUnit(BusinessUnitModelBean bean);

	public ServiceResult<BusinessUnitModelBean> updateBusinessUnit(BusinessUnitModelBean bean);

	public ServiceResult<Integer> deleteBusinessUnit(BusinessUnitModelBean bean);
}
