package th.co.locus.jlo.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.workflow.bean.BusinessRuleModelBean;
import th.co.locus.jlo.workflow.bean.WorkflowModelBean;

@Slf4j
@Service
public class BusinessRuleServiceImpl extends BaseService implements BusinessRuleService {
	@Autowired
	private WorkflowService workflowService;
	@Override
	public ServiceResult<Page<BusinessRuleModelBean>> getBusinessRulePagelist(BusinessRuleModelBean bean,PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("businessRule.getBusinessRuleList", bean, pageRequest));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<BusinessRuleModelBean> getBusinessRuleDetail(BusinessRuleModelBean bean) {
		try {
			
		}catch(Exception ex) {
			
		}
		return null;
	}

	@Override
	public ServiceResult<BusinessRuleModelBean> createBusinessRule(BusinessRuleModelBean bean) {
		try {
			WorkflowModelBean wfobj=new WorkflowModelBean();
			wfobj.setWorkflowId(bean.getWorkflowId());
			ServiceResult<WorkflowModelBean> wf=this.workflowService.getWorkflowDetail(wfobj);
			if(wf.getResult()!=null) {
				bean.setSystemId(wf.getResult().getSystemId());
			}
			int result=commonDao.insert("businessRule.createBusinessRule", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create business rule.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<BusinessRuleModelBean> updateBusinessRule(BusinessRuleModelBean bean) {
		try {
			int result=commonDao.update("businessRule.updateBusinessRule", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to edit business rule.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
		
	}

}
