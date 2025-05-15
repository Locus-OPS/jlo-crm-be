package th.co.locus.jlo.business.prompt_manage;

import th.co.locus.jlo.business.prompt_manage.bean.PromptManageCriteriaModelBean;
import th.co.locus.jlo.business.prompt_manage.bean.PromptManageModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface PromptManageService {
	
	public ServiceResult<Page<PromptManageModelBean>> searchPrompt(PromptManageCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PromptManageModelBean> createPrompt(PromptManageModelBean bean);
	public ServiceResult<PromptManageModelBean> updatePrompt(PromptManageModelBean bean);
}
