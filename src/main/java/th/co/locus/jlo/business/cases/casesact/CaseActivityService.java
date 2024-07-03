/**
 * 
 */
package th.co.locus.jlo.business.cases.casesact;

import th.co.locus.jlo.business.cases.casesact.bean.CaseActivityModelBean;
import th.co.locus.jlo.business.cases.casesact.bean.SearchCaseActivityModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

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
