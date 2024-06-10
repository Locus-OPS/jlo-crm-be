/**
 * 
 */
package th.co.locus.jlo.business.loyalty.casesact;

import th.co.locus.jlo.business.loyalty.casesact.bean.CaseActivityModelBean;
import th.co.locus.jlo.business.loyalty.casesact.bean.SearchCaseActivityModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

/**
 * @author Mr.BoonOom
 *
 */
public interface CaseActivityService {
	
	public ServiceResult<Page<CaseActivityModelBean>> getCaseActivityListByCaseNumber(SearchCaseActivityModelBean bean,
			PageRequest pageRequest);

	public ServiceResult<CaseActivityModelBean> createCaseActivity(CaseActivityModelBean bean);

	public ServiceResult<CaseActivityModelBean> updateCaseActivity(CaseActivityModelBean bean);

	public ServiceResult<Integer> deleteCaseActivity(CaseActivityModelBean bean);

}
