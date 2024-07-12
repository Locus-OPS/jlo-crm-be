package th.co.locus.jlo.business.cases;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Slf4j
@Service
public class CaseServiceImpl extends BaseService implements CaseService{

	@Override
	public ServiceResult<Page<CaseModelBean>> getCaseList(SearchCaseModelBean bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("case.getCaseList", bean, pageRequest));
	}
	
	@Override
	public ServiceResult<CaseModelBean> getCaseByCaseNumber(String caseNumber) {
		return success(commonDao.selectOne("case.getCaseByCaseNumber", caseNumber));
	}
	
	@Override
	public ServiceResult<CaseModelBean> createCase(CaseModelBean bean) {
		
		String caseNumber = commonDao.selectOne("case.generateCaseNumber", bean);
		if (caseNumber != null && caseNumber != "") {
			bean.setCaseNumber(caseNumber);
			//int result = commonDao.update("case.createCase", bean); 
			String result = commonDao.selectOne("case.createCaseProcedure", bean);
			log.info("result > "+result);
			if(result != null) {
				return success(commonDao.selectOne("case.getCaseByCaseNumber", caseNumber));
			}
		}
		return fail();
	}
	
	@Override
	public ServiceResult<CaseModelBean> updateCase(CaseModelBean bean) {
		
		int result = commonDao.update("case.updateCaseProc", bean);
		if (result > 0) {
			return success(commonDao.selectOne("case.getCaseByCaseNumber", bean));
		}
		return fail();
	}
}
