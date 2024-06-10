/**
 * 
 */
package th.co.locus.jlo.business.loyalty.cases;

import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.loyalty.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

/**
 * @author Mr.BoonOom
 *
 */
public interface CaseService {
	public ServiceResult<Page<CaseModelBean>> getCaseList(SearchCaseModelBean bean, PageRequest pageRequest);
	public ServiceResult<CaseModelBean> getCaseByCaseNumber(String bean);
	public ServiceResult<CaseModelBean> createCase(CaseModelBean bean);
	public ServiceResult<CaseModelBean> updateCase(CaseModelBean bean);
	
}
