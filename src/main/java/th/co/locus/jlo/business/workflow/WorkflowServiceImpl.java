package th.co.locus.jlo.business.workflow;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.workflow.bean.WorkflowModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Slf4j
@Service
public class WorkflowServiceImpl extends BaseService implements WorkflowService {

	@Override
	public ServiceResult<Page<WorkflowModelBean>> getWorkflowPageList(WorkflowModelBean bean,PageRequest page) {
		try {
			return success(commonDao.selectPage("workflow.getWorkflowList", bean, page));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowModelBean> getWorkflowDetail(WorkflowModelBean bean) {
		try {
			return success(commonDao.selectOne("workflow.getWorkflowDetail", bean));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<WorkflowModelBean> createWorkflow(WorkflowModelBean bean) {
		try {
			int result=commonDao.insert("workflow.createWorkflow", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to save data.");
		}catch(Exception ex) {
			return fail("500","Unable to save data.");
		}
	}

	@Override
	public ServiceResult<WorkflowModelBean> updateWorkflow(WorkflowModelBean bean) {
		try {
			int result=commonDao.update("workflow.updateWorkflow", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to edit data.");
		}catch(Exception ex) {
			return fail("500","Unable to edit data.");
		}
	}

}
