package th.co.locus.jlo.business.workflow;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.workflow.bean.WorkFlowTaskModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Service
public class WorkflowTaskServiceImpl extends BaseService implements WorkflowTaskService  {

	@Override
	public ServiceResult<Page<WorkFlowTaskModelBean>> getWorkflowTaskPageList(WorkFlowTaskModelBean bean,PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("workflowtask.getWorkflowTaskList", bean, pageRequest));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkFlowTaskModelBean> getWorkflowTaskDetail(WorkFlowTaskModelBean bean) {
		try {
			return success(commonDao.selectOne("workflowtask.getWorkflowTaskDetail", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkFlowTaskModelBean> createWorkflowTask(WorkFlowTaskModelBean bean) {
		try {
			int result=commonDao.insert("workflowtask.createWorkflowtask",bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create workflow task.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkFlowTaskModelBean> updateWorkflowTask(WorkFlowTaskModelBean bean) {
		try {
			int result=commonDao.update("workflowtask.updateWorkflowTask", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to update workflow task.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

}
