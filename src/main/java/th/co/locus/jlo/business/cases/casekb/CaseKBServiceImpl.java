package th.co.locus.jlo.business.cases.casekb;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.cases.casekb.bean.CaseKBModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
@Log4j2
@Service
public class CaseKBServiceImpl extends BaseService implements CaseKBService {

	@Override
	public ServiceResult<Page<CaseKBModelBean>> getRefKBList(CaseKBModelBean bean,PageRequest page) {
		try {
			return success(commonDao.selectPage("caseKB.getRefKBList", bean, page));
		}catch(Exception ex) {
			log.error("An unexpected error occurred: ",ex);
			return fail("500","An unexpected error occurred");
		}
		
	}

	@Override
	public ServiceResult<CaseKBModelBean> deleteRefKB(CaseKBModelBean bean) {
		try {
			int result=commonDao.delete("caseKB.deleteRefKB",bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to delete because data not found.");
		}catch(Exception ex) {
			log.error("An unexpected error occurred: ",ex);
			return fail("500","An unexpected error occurred");
		}
	}

	@Override
	public ServiceResult<CaseKBModelBean> createRefKB(CaseKBModelBean bean) {
		try {
			CaseKBModelBean dupResult=commonDao.selectOne("caseKB.getRefKBDetail",bean);
			if(dupResult!=null) {
				return fail("500","Already have this knowledge.");
			}
			int result=commonDao.insert("caseKB.createRefKB", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to create because something wrong.");
		}catch(Exception ex) {
			log.error("An unexpected error occurred:",ex);
			return fail("500","An unexpected error occurred");
			
		}
	}

}
