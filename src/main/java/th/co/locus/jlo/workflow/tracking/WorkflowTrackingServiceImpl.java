package th.co.locus.jlo.workflow.tracking;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingBean;

@Slf4j
@Service
public class WorkflowTrackingServiceImpl extends BaseService implements WorkflowTrackingService {

	@Override
	public WorkflowTrackingBean saveTracking(WorkflowTrackingBean tracking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkflowTrackingBean> getAllTracking() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkflowTrackingBean> getTrackingById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteTracking(Integer id) {
		// TODO Auto-generated method stub
		
	}

	 

}
