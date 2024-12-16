package th.co.locus.jlo.external.api;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.external.api.req.bean.ApiWorkflowRequestBean;
import th.co.locus.jlo.external.api.resp.bean.ApiWorkflowResponseBean;

public interface ApiWorkflowService {
	ServiceResult<ApiWorkflowResponseBean> getWorkflow(ApiWorkflowRequestBean bean);
}
