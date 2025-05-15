package th.co.locus.jlo.business.prompt_manage;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.prompt_manage.bean.PromptManageCriteriaModelBean;
import th.co.locus.jlo.business.prompt_manage.bean.PromptManageModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Service
public class PromptManageServiceImpl extends BaseService implements PromptManageService {

	@Override
	public ServiceResult<Page<PromptManageModelBean>> searchPrompt(PromptManageCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("prompt-manage.searchPrompt", criteria, pageRequest));
	}

	@Override
	public ServiceResult<PromptManageModelBean> createPrompt(PromptManageModelBean bean) {
		try {
			int result = commonDao.update("prompt-manage.createPrompt", bean);
			if (result > 0) {
				return success(commonDao.selectOne("prompt-manage.findPromptById", bean));
			}
			return fail();
		} catch (DuplicateKeyException e) {
			return fail("501", e.getMessage());
		}
	}

	@Override
	public ServiceResult<PromptManageModelBean> updatePrompt(PromptManageModelBean bean) {
		int result = commonDao.update("prompt-manage.updatePrompt", bean);
		if (result > 0) {
			return success(commonDao.selectOne("prompt-manage.findPromptById", bean));
		}
		return fail();
	}

}
