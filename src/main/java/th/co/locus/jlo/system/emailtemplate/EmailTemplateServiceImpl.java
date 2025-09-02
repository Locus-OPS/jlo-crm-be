package th.co.locus.jlo.system.emailtemplate;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.emailtemplate.bean.EmailTemplateModelBean;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Service
public class EmailTemplateServiceImpl extends BaseService implements EmailTemplateService {
	
	@Autowired
	private FileService fileService;
	
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

	@Override
	public ServiceResult<EmailTemplateModelBean> createEmailTemplate(EmailTemplateModelBean bean,
			FileModelBean fileBean, MultipartFile file) throws IOException {
		 	
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			bean.setAttId(fileBean.getAttId());
		}
		
		int result = commonDao.update("emailTemplate.createEmailTemplate", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailTemplate.findEmailTemplateById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<EmailTemplateModelBean> updateEmailTemplate(EmailTemplateModelBean bean,
			FileModelBean fileBean, MultipartFile file) throws IOException {
		
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			bean.setAttId(fileBean.getAttId());
		}
		 
		int result = commonDao.update("emailTemplate.updateEmailTemplate", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailTemplate.findEmailTemplateById", bean));
		}
		return fail();
		
	}

	@Override
	public ServiceResult<EmailTemplateModelBean> getEmailTemplateByModule(EmailTemplateModelBean criteria) {
		return success(commonDao.selectOne("emailTemplate.findEmailTemplateByModule", criteria));
	}

}
