package th.co.locus.jlo.workflow.tracking;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingBean;

@Service
public class WorkflowTrackingServiceImpl extends BaseService implements WorkflowTrackingService {@Override
	public ServiceResult<WorkflowTrackingBean> createWfTracking(WorkflowTrackingBean bean) {
		try {
			int result=commonDao.insert("wftracking.createWfTracking",bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create workflow tracking.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<List<WorkflowTrackingBean>> getAllWfTracking() {
		try {
			return success(commonDao.selectList("wftracking.getWfTrackingListAll"));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<WorkflowTrackingBean>> getWfTrackingById(WorkflowTrackingBean bean) {
		try {
			return success(commonDao.selectList("wftracking.getWfTrackingById",bean ));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<WorkflowTrackingBean> deleteWftrackingById(WorkflowTrackingBean bean) {
		try {
			ServiceResult<WorkflowTrackingBean> wfTracking=this.getWftrackingDetail(bean);
			if(wfTracking.getResult()!=null) {
				int result=commonDao.delete("wftracking.deleteWfTracking",Map.of("trackingId",bean.getTrackingId()));
				if(result>0) {
					return success(wfTracking.getResult());
				}
			}
//			int result=commonDao.delete("wftracking.deleteWfTracking",Map.of("trackingId",bean.getTrackingId()));
//			if(result>0) {
//				return success(bean);
//			}
			return fail("500","Unable to delete workflow tracking.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}
	
	private ServiceResult<WorkflowTrackingBean> getWftrackingDetail(WorkflowTrackingBean bean) {
		try {
			return success(commonDao.selectOne("wftracking.getWftrackingDetail", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<WorkflowTrackingBean>> getWfTrackingByWorkflowId(WorkflowTrackingBean bean,PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("wftracking.getWftrackingByWorkflowId",bean,pageRequest));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

}
