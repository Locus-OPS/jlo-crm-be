package th.co.locus.jlo.business.workflow;

import th.co.locus.jlo.business.workflow.bean.BusinessRuleModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface BusinessRuleService {
	ServiceResult<Page<BusinessRuleModelBean>> getBusinessRulePagelist(BusinessRuleModelBean bean,PageRequest pageRequest);
	ServiceResult<BusinessRuleModelBean> getBusinessRuleDetail(BusinessRuleModelBean bean);
	ServiceResult<BusinessRuleModelBean> createBusinessRule(BusinessRuleModelBean bean);
	ServiceResult<BusinessRuleModelBean> updateBusinessRule(BusinessRuleModelBean bean);
}
