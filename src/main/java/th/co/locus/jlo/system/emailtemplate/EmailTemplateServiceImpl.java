package th.co.locus.jlo.system.emailtemplate;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.emailtemplate.bean.EmailTemplateModelBean;

@Service
public class EmailTemplateServiceImpl extends BaseService implements EmailTemplateService {

	@Override
	public ServiceResult<Page<EmailTemplateModelBean>> getEmailTemplateList(EmailTemplateModelBean criteria,
			PageRequest pageRequest) {
		 	
		return success(commonDao.selectPage("emailTemplate.getEmailTemplateList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<EmailTemplateModelBean> createEmailTemplate(EmailTemplateModelBean bean) {
		int result = commonDao.update("emailTemplate.createEmailTemplate", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailTemplate.findEmailTemplateById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<EmailTemplateModelBean> updateEmailTemplate(EmailTemplateModelBean bean) {
		int result = commonDao.update("emailTemplate.updateEmailTemplate", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailTemplate.findEmailTemplateById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteEmailTemplate(EmailTemplateModelBean bean) {
		return success(commonDao.delete("emailTemplate.deleteEmailTemplate", bean));
	}

}
