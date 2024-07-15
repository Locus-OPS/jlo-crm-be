package th.co.locus.jlo.system.emailtemplate;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.emailtemplate.bean.EmailTemplateModelBean;

public interface EmailTemplateService {

	public ServiceResult<Page<EmailTemplateModelBean>> getEmailTemplateList(EmailTemplateModelBean criteria, PageRequest pageRequest);

	public ServiceResult<EmailTemplateModelBean> createEmailTemplate(EmailTemplateModelBean bean);

	public ServiceResult<EmailTemplateModelBean> updateEmailTemplate(EmailTemplateModelBean bean);

	public ServiceResult<Integer> deleteEmailTemplate(EmailTemplateModelBean bean);
}
