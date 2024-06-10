package th.co.locus.jlo.business.loyalty.manualpoint;

import th.co.locus.jlo.business.loyalty.manualpoint.bean.ManualPointCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.manualpoint.bean.ManualPointModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface ManualPointService {

	public ServiceResult<Page<ManualPointModelBean>> seachManualPoint(ManualPointCriteriaModelBean bean,
			PageRequest pageRequest);
	public ServiceResult<ManualPointModelBean> insertManualPoint(ManualPointModelBean bean);
	public ServiceResult<ManualPointModelBean> findManualPointById(Long txnId);
	//public ServiceResult<ManualPointModelBean> updateManualPoint(ManualPointModelBean bean);

}
