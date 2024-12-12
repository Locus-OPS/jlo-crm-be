package th.co.locus.jlo.workflow;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
