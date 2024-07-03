package th.co.locus.jlo.system.codebook;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.codebook.bean.CodebookModelBean;
import th.co.locus.jlo.system.codebook.bean.SearchCodebookCriteriaModelBean;

@Service
public class CodebookServiceImpl extends BaseService implements CodebookService {

	@Override
	public ServiceResult<Page<CodebookModelBean>> searchCodebook(SearchCodebookCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("codebook.searchCodebook", criteria, pageRequest));
	}

	@Override
	public ServiceResult<CodebookModelBean> createCodebook(CodebookModelBean bean) {
		int result = commonDao.update("codebook.createCodebook", bean);
		if (result > 0) {
			return success(commonDao.selectOne("codebook.findCodebookById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<CodebookModelBean> updateCodebook(CodebookModelBean bean) {
		int result = commonDao.update("codebook.updateCodebook", bean);
		if (result > 0) {
			return success(commonDao.selectOne("codebook.findCodebookById", bean));
		}
		return fail();
	}

}
