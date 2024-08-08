/**
 * 
 */
package th.co.locus.jlo.business.sr;

import th.co.locus.jlo.business.sr.bean.SearchSrModelBean;
import th.co.locus.jlo.business.sr.bean.ServiceRequestModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

/**
 * @author Mr.BoonOom
 *
 */
public interface ServiceRequestService {
	public ServiceResult<Page<ServiceRequestModelBean>> getSrList(SearchSrModelBean bean, PageRequest pageRequest);
	public ServiceResult<ServiceRequestModelBean> getSrBySrNumber(String bean);
	public ServiceResult<ServiceRequestModelBean> createSr(ServiceRequestModelBean bean);
	public ServiceResult<ServiceRequestModelBean> updateSr(ServiceRequestModelBean bean);
	
}
