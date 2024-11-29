package th.co.locus.jlo.business.workflow;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.workflow.bean.WorkflowTaskAssignModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Service
public class WorkflowTaskAssignServiceImpl extends BaseService implements WorkflowTaskAssignService {

	@Override
	public ServiceResult<Page<WorkflowTaskAssignModelBean>> getWorkflowTaskAssignPageList(WorkflowTaskAssignModelBean bean, PageRequest pageRequest) {
		try {
			
		}catch(Exception ex) {
			
		}
		return null;
	}

	@Override
	public ServiceResult<WorkflowTaskAssignModelBean> getWorkflowTaskAssignDetail(WorkflowTaskAssignModelBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<WorkflowTaskAssignModelBean> createWorkflowTaskAssign(WorkflowTaskAssignModelBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<WorkflowTaskAssignModelBean> updateWorkflowTaskAssign(WorkflowTaskAssignModelBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}
