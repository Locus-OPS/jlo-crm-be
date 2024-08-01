package th.co.locus.jlo.system.email.emaillog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.attachment.AttachmentMgntService;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.system.email.EmailService;
import th.co.locus.jlo.system.email.bean.EmailBean;
import th.co.locus.jlo.system.email.emaillog.bean.EmailLogBean;
import th.co.locus.jlo.system.email.emaillog.bean.SearchEmailLogBean;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Slf4j
@RestController
@RequestMapping("api/emailLog")
public class EmailLogController extends BaseController {
	@Autowired
	private EmailLogService emailLogService;

	@Autowired
	private AttachmentMgntService attachmentMgntService;

	@Autowired
	private FileService fileService;

	@Autowired
	private EmailService emailService;

	@Value("${jlo.mail.from}")
	private String mailFrom;
	
	
	@PostMapping(value = "/getEmailLogList", produces = "application/json")
	public ApiPageResponse<List<EmailLogBean>> getLegalList(@RequestBody ApiPageRequest<SearchEmailLogBean> request) {

		PageRequest pageRequest = getPageRequest(request);
		SearchEmailLogBean logbean = request.getData();
		logbean.setBuId(getBuId());
		
		ServiceResult<Page<EmailLogBean>> serviceResult = new ServiceResult<Page<EmailLogBean>>();

		serviceResult = emailLogService.getEmailLogList(logbean, pageRequest);

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	
	@PostMapping(value = "/resendEmailLog", produces = "application/json")
	public ApiResponse<EmailBean> resendEmailLog(@RequestBody ApiRequest<EmailLogBean> request) {
		
			EmailLogBean logBean = request.getData();
		try {
			EmailBean emailInfo = new EmailBean();
			ServiceResult<EmailLogBean> resultLog = emailLogService.findEmailById(logBean);
			logBean = resultLog.getResult();
			
			String emailCcReq = "";
			String subjectReq = "";
			String messageDesc = "";
			String fromName = "";
			String fromEmail = logBean.getFormEmail();
			String emailTo = "";
			String emailCc = "";

			String[] arrayEmailTo = null;
			String[] arrayEmailCc= null;
			String[] arrEmailBcc = { null };
			Map<String, Object> params = null;
			
			if(logBean.getToEmail() != null && !logBean.getToEmail().trim().isEmpty()) {
				emailTo = logBean.getToEmail().replaceAll("\\s", "");
				emailInfo.setToEmail(emailTo);
				arrayEmailTo = emailTo.split(",");
			}
			
			if(logBean.getCcEmail() != null && !logBean.getCcEmail().trim().isEmpty()) {
				emailCcReq = logBean.getCcEmail();
				emailInfo.setCcEmail(emailCcReq);
				emailCc = emailCcReq.replaceAll("\\s", "");
				arrayEmailCc = emailCc.split(",");
			}
			
			if(logBean.getSubjectEmail() != null && !logBean.getSubjectEmail().trim().isEmpty()) {
				subjectReq = logBean.getSubjectEmail();
				emailInfo.setSubjectEmail(subjectReq);				
			}
			
			if(logBean.getBodyEmail() != null && !logBean.getBodyEmail().trim().isEmpty()) {
				messageDesc = logBean.getBodyEmail();
				emailInfo.setBodyEmail(messageDesc);
			}
						
			if(logBean.getAttachmentId() != null && logBean.getAttachmentId() != 0) {
				
				FileModelBean fileModelBean = new FileModelBean();
				ServiceResult<FileModelBean> serviceResult = attachmentMgntService.getAttachmentByAttId(logBean.getAttachmentId());
				fileModelBean = serviceResult.getResult();	

				String filePath = fileModelBean.getFilePath();
				Resource fileObject = fileService.loadFile(filePath);
				log.debug("Path >>:>>  " + fileObject.getFile().getPath());
				// Open streams.
				File fileAtt = new File(fileObject.getFile().getPath());
				
				emailService.sendEmailAttTemplate(fromEmail, arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, params, fileAtt,fileAtt.getName());
			}else {
				emailService.sendEmailTemplate(fromEmail, arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, "",null);
			}					 
		
			logBean.setUpdatedBy(getUserId());
			logBean.setStatusCode("1");
			logBean.setStatusDesc("Successfully");
			emailLogService.updateEmailLog(logBean);

			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			return ApiResponse.success(sendEmailResult.getResult());

		} catch (Exception e) {
			//logBean = new EmailLogBean();
			//logBean.setId(logBean.getId());
			logBean.setUpdatedBy(getUserId());
			logBean.setStatusCode("0");
			logBean.setStatusDesc("	Failure");
			logBean.setDeliverDesc(e.getMessage());
			emailLogService.updateEmailLog(logBean);

			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setSuccess(false);
			sendEmailResult.setResponseDescription(e.getMessage());
			sendEmailResult.setResponseCode("500");

			e.printStackTrace();

			return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
		}
	}

}
