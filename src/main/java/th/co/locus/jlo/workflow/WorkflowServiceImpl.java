package th.co.locus.jlo.workflow;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.workflow.bean.WorkflowModelBean;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingGraphBean;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingLinkBean;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingNodeBean;

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
				commonDao.update("workflow.updateSytemIdWfBusinessRule", bean);
				commonDao.update("workflow.updateSytemIdWfTask", bean);
				return success(bean);
			}
			return fail("500","Unable to edit data.");
		}catch(Exception ex) {
			return fail("500","Unable to edit data.");
		}
	}

	@Override
	public ServiceResult<WorkflowTrackingGraphBean> getWorkflowGraphPreview(WorkflowModelBean bean) {
		try {
			WorkflowTrackingGraphBean graph=new WorkflowTrackingGraphBean();
			List<WorkflowTrackingNodeBean> nodeList=commonDao.selectList("workflow.getWfNodeList",bean);
			if(nodeList.size()>0) {
				graph.setNode(nodeList);
			}
			List<WorkflowTrackingLinkBean> linkList=commonDao.selectList("workflow.getWfLinkList",bean);
			if(linkList.size()>0) {
				graph.setLink(linkList);
			}
			return success(graph);
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
		
	}

}
