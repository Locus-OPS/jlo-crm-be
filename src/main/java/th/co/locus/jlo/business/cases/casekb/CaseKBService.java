package th.co.locus.jlo.business.cases.casekb;

import th.co.locus.jlo.business.cases.casekb.bean.CaseKBModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface CaseKBService {
	ServiceResult<Page<CaseKBModelBean>> getRefKBList(CaseKBModelBean bean,PageRequest page);
	ServiceResult<CaseKBModelBean> deleteRefKB(CaseKBModelBean bean);
	ServiceResult<CaseKBModelBean> createRefKB(CaseKBModelBean bean);
}
