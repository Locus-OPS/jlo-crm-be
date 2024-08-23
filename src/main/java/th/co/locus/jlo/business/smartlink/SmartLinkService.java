package th.co.locus.jlo.business.smartlink;

import th.co.locus.jlo.business.smartlink.bean.SaveSmartLinkModelBean;
import th.co.locus.jlo.business.smartlink.bean.SmartLinkModelBean;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface SmartLinkService {
	
	public ServiceResult<SmartLinkModelBean> getSmartLinkByHashKey(String hashKey);

    public ServiceResult<SmartLinkModelBean> getSmartLinkByHashKeyNotExpire(String hashKey);

    public ServiceResult<SmartLinkModelBean> createSmartLink(SaveSmartLinkModelBean bean);
}
