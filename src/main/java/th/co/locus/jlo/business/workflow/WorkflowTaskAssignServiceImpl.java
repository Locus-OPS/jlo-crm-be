package th.co.locus.jlo.business.workflow;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.workflow.bean.WorkflowTaskAssignModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
@Slf4j
@Service
public class WorkflowTaskAssignServiceImpl extends BaseService implements WorkflowTaskAssignService {

	@Override
	public ServiceResult<Page<WorkflowTaskAssignModelBean>> getWorkflowTaskAssignPageList(WorkflowTaskAssignModelBean bean, PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("workflowtaskassign.getWorkflowTaskAssignPageList", bean, pageRequest));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowTaskAssignModelBean> getWorkflowTaskAssignDetail(WorkflowTaskAssignModelBean bean) {
		try {
			return success(commonDao.selectOne("workflowtaskassign.getWorkflowTaskAssignDetail", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowTaskAssignModelBean> createWorkflowTaskAssign(WorkflowTaskAssignModelBean bean) {
		try {
			log.info("Hello :"+bean.toString());
			int result=commonDao.insert("workflowtaskassign.createWorkflowTaskAssign",bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create workflow task assign.");
		}catch(Exception ex) {
			log.error("Error : "+ex.getStackTrace().toString());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowTaskAssignModelBean> updateWorkflowTaskAssign(WorkflowTaskAssignModelBean bean) {
		try {
			int result=commonDao.update("workflowtaskassign.updateWorkflowTaskAssign", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to update workflow task assign.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}

	}

}
