/**
 * 
 */
package th.co.locus.jlo.business.cases;

import java.io.ByteArrayOutputStream;

import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

/**
 * @author Mr.BoonOom
 *
 */
public interface CaseService {
	public ServiceResult<Page<CaseModelBean>> getCaseList(SearchCaseModelBean bean, PageRequest pageRequest);
	public ServiceResult<CaseModelBean> getCaseByCaseNumber(String bean);
	public ServiceResult<CaseModelBean> createCase(CaseModelBean bean);
	public ServiceResult<CaseModelBean> updateCase(CaseModelBean bean);
	
	 public ServiceResult<ByteArrayOutputStream> exportCaseReport();
}
