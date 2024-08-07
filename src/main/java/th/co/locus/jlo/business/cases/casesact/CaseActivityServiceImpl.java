/**
 * 
 */
package th.co.locus.jlo.business.cases.casesact;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import th.co.locus.jlo.business.cases.casesact.bean.CaseActivityModelBean;
import th.co.locus.jlo.business.cases.casesact.bean.SearchCaseActivityModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

/**
 * @author Mr.BoonOom
 *
 */
@Service
public class CaseActivityServiceImpl extends BaseService implements CaseActivityService {

	@Override
	public ServiceResult<Page<CaseActivityModelBean>> getCaseActivityListByCaseNumber(SearchCaseActivityModelBean bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("caseAct.getCaseActivityListByCaseNumber", bean, pageRequest));
	}

	@Override
	public ServiceResult<CaseActivityModelBean> createCaseActivity(CaseActivityModelBean bean) {
		String actNumber = commonDao.selectOne("caseAct.generateCaseActNumber", bean);
		if(!StringUtils.isEmpty(actNumber)){		
			bean.setActivityNumber(actNumber);			
			int result = commonDao.update("caseAct.createCaseActivity", bean);
			if(result > 0) {
				return success(commonDao.selectOne("caseAct.getCaseActivityByActivityNumber", actNumber));
			}
		}
		return fail();
	}

	@Override
	public ServiceResult<CaseActivityModelBean> updateCaseActivity(CaseActivityModelBean bean) {
		int result = commonDao.update("caseAct.updateCaseActivity", bean);
		if (result > 0) {
			return success(commonDao.selectOne("caseAct.getCaseActivityByActivityNumber", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteCaseActivity(CaseActivityModelBean bean) {
		return success(commonDao.delete("caseAct.deleteCaseActivity", bean));
	}

}
