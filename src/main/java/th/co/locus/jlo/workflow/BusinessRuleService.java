package th.co.locus.jlo.workflow;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.workflow.bean.BusinessRuleModelBean;

public interface BusinessRuleService {
	ServiceResult<Page<BusinessRuleModelBean>> getBusinessRulePagelist(BusinessRuleModelBean bean,PageRequest pageRequest);
	ServiceResult<BusinessRuleModelBean> getBusinessRuleDetail(BusinessRuleModelBean bean);
	ServiceResult<BusinessRuleModelBean> createBusinessRule(BusinessRuleModelBean bean);
	ServiceResult<BusinessRuleModelBean> updateBusinessRule(BusinessRuleModelBean bean);
}
