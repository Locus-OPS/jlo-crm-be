package th.co.locus.jlo.system.emailtemplate;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.emailtemplate.bean.EmailTemplateModelBean;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

public interface EmailTemplateService {

	public ServiceResult<Page<EmailTemplateModelBean>> getEmailTemplateList(EmailTemplateModelBean criteria, PageRequest pageRequest);
	
	public ServiceResult<EmailTemplateModelBean> getEmailTemplateByModule(EmailTemplateModelBean criteria);
	
	public ServiceResult<EmailTemplateModelBean> createEmailTemplate(EmailTemplateModelBean bean);

	public ServiceResult<EmailTemplateModelBean> updateEmailTemplate(EmailTemplateModelBean bean);

	public ServiceResult<Integer> deleteEmailTemplate(EmailTemplateModelBean bean);
	
	
	public ServiceResult<EmailTemplateModelBean> createEmailTemplate(EmailTemplateModelBean bean, FileModelBean fileBean, MultipartFile file) throws IOException;
	
	public ServiceResult<EmailTemplateModelBean> updateEmailTemplate(EmailTemplateModelBean bean, FileModelBean fileBean, MultipartFile file) throws IOException;
	 
}
