package th.co.locus.jlo.workflow.tracking;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingBean;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingGraphBean;

public interface WorkflowTrackingService {
	public ServiceResult<WorkflowTrackingBean> createWfTracking(WorkflowTrackingBean bean);
	public ServiceResult<List<WorkflowTrackingBean>> getAllWfTracking();
	public ServiceResult<List<WorkflowTrackingBean>> getWfTrackingById(WorkflowTrackingBean bean);
	public ServiceResult<Page<WorkflowTrackingBean>> getWfTrackingByWorkflowId(WorkflowTrackingBean bean,PageRequest pageRequest);
	public ServiceResult<WorkflowTrackingBean> deleteWftrackingById(WorkflowTrackingBean bean);
	public ServiceResult<WorkflowTrackingGraphBean> getWftrackingGraph(WorkflowTrackingBean bean);
	
}
