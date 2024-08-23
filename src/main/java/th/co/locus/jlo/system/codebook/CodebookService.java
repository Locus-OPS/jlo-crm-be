package th.co.locus.jlo.system.codebook;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.codebook.bean.CodebookModelBean;
import th.co.locus.jlo.system.codebook.bean.SearchCodebookCriteriaModelBean;

public interface CodebookService {
	
	public ServiceResult<Page<CodebookModelBean>> searchCodebook(SearchCodebookCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<List<CodebookModelBean>> searchCodebookByCodeTypes(String[] codeTypes);
	public ServiceResult<CodebookModelBean> createCodebook(CodebookModelBean bean);
	public ServiceResult<CodebookModelBean> updateCodebook(CodebookModelBean bean);
	public ServiceResult<List<CodebookModelBean>> searchCodebookByCodeTypeList(List<String> codeTypeList);	
	public ServiceResult<List<CodebookModelBean>> searchCodebookNoPage(SearchCodebookCriteriaModelBean criteria);
}
