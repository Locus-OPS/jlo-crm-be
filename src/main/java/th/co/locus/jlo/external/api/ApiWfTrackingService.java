package th.co.locus.jlo.external.api;

import java.util.List;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.external.api.req.bean.ApiWfTrackingRequestBean;
import th.co.locus.jlo.external.api.resp.bean.ApiWfTrackingRespBean;

public interface ApiWfTrackingService {
	public ServiceResult<ApiWfTrackingRespBean> createWfTracking(ApiWfTrackingRequestBean bean);
	public ServiceResult<List<ApiWfTrackingRespBean>> getWfTrackingByTransactionid(ApiWfTrackingRequestBean bean);
	public ServiceResult<ApiWfTrackingRespBean> getWfTrackingByTrackingId(ApiWfTrackingRequestBean bean);
	public ServiceResult<ApiWfTrackingRespBean> deleteWftrackingById(ApiWfTrackingRequestBean bean);

	
}
