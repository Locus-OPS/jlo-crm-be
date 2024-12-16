package th.co.locus.jlo.external.api;

import java.util.List;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.external.api.req.bean.ApiWfTrackingRequestBean;
import th.co.locus.jlo.external.api.resp.bean.ApiWfTrackingRespBean;

@Service
public class ApiWfTrackingServiceImpl extends BaseService implements ApiWfTrackingService {

	@Override
	public ServiceResult<ApiWfTrackingRespBean> createWfTracking(ApiWfTrackingRequestBean bean) {
		try {
			int result=commonDao.insert("apiwftracking.createWfTracking", bean);
			if(result>0) {
				return success(this.getWfTrackingByTrackingId(bean).getResult());
			}
			return fail("500","Unable to create workflow tracking.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<ApiWfTrackingRespBean>> getWfTrackingByTransactionid(ApiWfTrackingRequestBean bean) {
		try {
			return success(commonDao.selectList("apiwftracking.getWfTrackingbyTransactionId", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<ApiWfTrackingRespBean> deleteWftrackingById(ApiWfTrackingRequestBean bean) {
		try {
			ServiceResult<ApiWfTrackingRespBean> wfTracking=this.getWfTrackingByTrackingId(bean);
			if(wfTracking.getResult()!=null) {
				int result=commonDao.delete("apiwftracking.deleteWfTracking", wfTracking.getResult());
				if(result>0) {
					return success(wfTracking.getResult());
				}
			}
			return fail("500","Unable to delete workflow tracking.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<ApiWfTrackingRespBean> getWfTrackingByTrackingId(ApiWfTrackingRequestBean bean) {
		try {
			return success(commonDao.selectOne("apiwftracking.getWfTrackingbyTrackingId", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

}
