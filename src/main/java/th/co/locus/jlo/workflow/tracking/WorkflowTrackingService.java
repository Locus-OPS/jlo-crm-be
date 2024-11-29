package th.co.locus.jlo.workflow.tracking;

import java.util.List;
import java.util.Optional;

import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingBean;

public interface WorkflowTrackingService {

	// Create or Update a Workflow Tracking
	public WorkflowTrackingBean saveTracking(WorkflowTrackingBean tracking);

	// Get all Workflow Trackings
	public List<WorkflowTrackingBean> getAllTracking();

	// Get a specific Workflow Tracking by ID
	public Optional<WorkflowTrackingBean> getTrackingById(Integer id);

	// Delete a Workflow Tracking by ID
	public void deleteTracking(Integer id);
}
