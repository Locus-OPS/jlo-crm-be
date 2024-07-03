package th.co.locus.jlo.system.codebook;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.codebook.bean.CodebookModelBean;
import th.co.locus.jlo.system.codebook.bean.SearchCodebookCriteriaModelBean;

public interface CodebookService {
	
	public ServiceResult<Page<CodebookModelBean>> searchCodebook(SearchCodebookCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<CodebookModelBean> createCodebook(CodebookModelBean bean);
	public ServiceResult<CodebookModelBean> updateCodebook(CodebookModelBean bean);
	
}
