package th.co.locus.jlo.workflow;

import java.util.List;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.workflow.bean.WorkflowSystemModelBean;

@Service
public class WorkflowSystemServiceImpl extends BaseService implements WorkflowSystemService {

	@Override
	public ServiceResult<List<WorkflowSystemModelBean>> getWfSytemList() {
		try {
			return success(commonDao.selectList("workflowsystem.getWorkflowSystemList"));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<WorkflowSystemModelBean>> getWfsytemPageList(WorkflowSystemModelBean bean,
			PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("workflowsystem.serachWorkflowSystemList", bean, pageRequest));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowSystemModelBean> createWfSystem(WorkflowSystemModelBean bean) {
		try {
			int result=commonDao.insert("workflowsystem.createWorkflowSystem", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create workflow system.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowSystemModelBean> updateWfsystem(WorkflowSystemModelBean bean) {
		try {
			int result=commonDao.update("workflowsystem.updateWorkflowSystem", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to update workflow system.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowSystemModelBean> getWfsystemDetail(WorkflowSystemModelBean bean) {
		try {
			return success(commonDao.selectOne("workflowsystem.getWorkflowSystemDetail", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

}
