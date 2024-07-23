/**
 * 
 */
package th.co.locus.jlo.system.email;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.attachment.AttachmentMgntService;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.system.email.bean.EmailBean;
import th.co.locus.jlo.system.email.emaillog.EmailLogService;
import th.co.locus.jlo.system.email.emaillog.bean.EmailLogBean;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

/**
 * @author apichat
 *
 */
@Slf4j 
@RestController
@RequestMapping("api/email")
public class EmailCommonController extends BaseController {
	
	public static final String EMAIL_TEMPLATE_ACTIVITY_NOTIC = "emailTemplates/webportal_email_nofication.vm";

	@Autowired
	private EmailService emailService;

	@Autowired
	private AttachmentMgntService attachmentMgntService;

	@Autowired
	private FileService fileService;

	@Autowired
	private EmailLogService emailLogService;

	@Value("${attachment.path.att}")
	private String destinationPath;

	@Value("${jlo.mail.from}")
	private String mailFrom;
	
	@Value("${spring.mail.port}")
	private String port;

	@PostMapping(value = "/sendEmail", produces = "application/json")
	public @ResponseBody ApiResponse<EmailBean> sendEmail(@RequestBody ApiRequest<EmailBean> request) throws Exception {

		EmailBean emailInfo = new EmailBean();

		try {

			emailInfo = request.getData();

			String emailCcReq = "";
			String subjectReq = "";
			String messageDesc = "";
			String fromName = "Jlo CRM";
			String emailTo = "";
			String emailCc = "";
						
			if(emailInfo.getCcEmail() != null && !emailInfo.getCcEmail().trim().isEmpty()) {
				emailCcReq = emailInfo.getCcEmail();
			}
			
			if(emailInfo.getSubjectEmail() != null && !emailInfo.getSubjectEmail().trim().isEmpty()) {
				subjectReq = emailInfo.getSubjectEmail();
			}
			
			if(emailInfo.getBodyEmail() != null && !emailInfo.getBodyEmail().trim().isEmpty()) {
				messageDesc = emailInfo.getBodyEmail();
			}
					 
			//emailCcReq = (StringUtils.isEmpty(emailInfo.getCcEmail()) ? "" : emailInfo.getCcEmail());
			//subjectReq = (StringUtils.isEmpty(emailInfo.getSubjectEmail()) ? " no subject " : emailInfo.getSubjectEmail());
			//messageDesc = (StringUtils.isEmpty(emailInfo.getBodyEmail()) ? "" : emailInfo.getBodyEmail());

			String templateName = EMAIL_TEMPLATE_ACTIVITY_NOTIC;
			
			 
			emailTo = emailInfo.getToEmail().replaceAll("\\s", "");
			String[] arrayEmailTo = emailTo.replaceAll(";", ",").split(",");

			emailCc = emailCcReq.replaceAll("\\s", "");
			String[] arrayEmailCc = emailCc.replaceAll(";", ",").split(",");
			String[] arrEmailBcc = { null };

			
			emailService.sendEmailTemplate(emailInfo.getFromEmail(), arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, "",null);
			
			log.debug("SendEmail Controller");
			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setResult(emailInfo);
			sendEmailResult.setSuccess(true);
			sendEmailResult.setResponseCode("200");
			sendEmailResult.setResponseDescription("Delivered successfully");

			EmailLogBean logBean = new EmailLogBean();
			logBean.setParentId(emailInfo.getParentId());
			logBean.setParentModule(emailInfo.getParentModule());
			logBean.setToEmail(emailInfo.getToEmail());
			logBean.setCcEmail(emailInfo.getCcEmail());
			logBean.setSubjectEmail(emailInfo.getSubjectEmail());
			logBean.setBodyEmail(emailInfo.getBodyEmail());
			logBean.setAttachmentId(emailInfo.getAttachmentId());
			logBean.setCreatedBy(getUserId());
			logBean.setUpdatedBy(getUserId());

			if (sendEmailResult.isSuccess()) {

				logBean.setStatusCode("1");
				logBean.setStatusDesc("Successfully");

			//	emailLogService.createEmailLog(logBean);

				return ApiResponse.success(sendEmailResult.getResult());
			} else {

				logBean.setStatusCode("2");
				logBean.setStatusDesc("	Failure");

			//	emailLogService.createEmailLog(logBean);

				sendEmailResult = new ServiceResult<EmailBean>();
				sendEmailResult.setResult(emailInfo);
				sendEmailResult.setSuccess(false);
				sendEmailResult.setResponseCode("500");
				sendEmailResult.setResponseDescription("Delivery Failure");

				return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
			}

		} catch (Exception e) {
			EmailLogBean logBean = new EmailLogBean();
			logBean.setParentId(emailInfo.getParentId());
			logBean.setParentModule(emailInfo.getParentModule());
			logBean.setToEmail(emailInfo.getToEmail());
			logBean.setCcEmail(emailInfo.getCcEmail());
			logBean.setSubjectEmail(emailInfo.getSubjectEmail());
			logBean.setBodyEmail(emailInfo.getBodyEmail());
			logBean.setAttachmentId(emailInfo.getAttachmentId());

			logBean.setCreatedBy(getUserId());
			logBean.setUpdatedBy(getUserId());
			logBean.setStatusCode("2");
			logBean.setStatusDesc("	Failure");
			logBean.setDeliverDesc(e.getMessage());
		//	emailLogService.createEmailLog(logBean);

			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setResult(emailInfo);
			sendEmailResult.setSuccess(false);
			sendEmailResult.setResponseDescription(e.getMessage());
			sendEmailResult.setResponseCode("500");

			e.printStackTrace();

			return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
		}
	}


	@PostMapping(value = "/sendEmailWithAtt", produces = "application/json")
	public ApiResponse<EmailBean> sendEmailWithAtt(@RequestBody ApiRequest<EmailBean> request) {

		EmailBean emailInfo = new EmailBean();

		try {

			emailInfo = request.getData();

			String emailCcReq = "";
			String subjectReq = "";
			String messageDesc = "";
			String fromName = "JLO CRM System";
			String emailTo = "";
			String emailCc = "";

			if(emailInfo.getCcEmail() != null && !emailInfo.getCcEmail().trim().isEmpty()) {
				emailCcReq = emailInfo.getCcEmail();
			}
			
			if(emailInfo.getSubjectEmail() != null && !emailInfo.getSubjectEmail().trim().isEmpty()) {
				subjectReq = emailInfo.getSubjectEmail();
			}
			
			if(emailInfo.getBodyEmail() != null && !emailInfo.getBodyEmail().trim().isEmpty()) {
				messageDesc = emailInfo.getBodyEmail();
			}
			
			String templateName = EMAIL_TEMPLATE_ACTIVITY_NOTIC;
			
			 
			emailTo = emailInfo.getToEmail().replaceAll("\\s", "");
			String[] arrayEmailTo = emailTo.replaceAll(";", ",").split(",");

			emailCc = emailCcReq.replaceAll("\\s", "");
			String[] arrayEmailCc = emailCc.replaceAll(";", ",").split(",");
			String[] arrEmailBcc = { null };

			FileModelBean fileModelBean = new FileModelBean();
			ServiceResult<FileModelBean> serviceResult = attachmentMgntService.getAttachmentByAttId(Long.valueOf(emailInfo.getAttachmentId()));

			fileModelBean = serviceResult.getResult();

			String filePath = fileModelBean.getFilePath();
			Resource fileObject = fileService.loadFile(filePath);
			log.debug("Path >>:>>  " + fileObject.getFile().getPath());
			log.debug("port " + port);
			// Open streams.
			File myFilePath = new File(fileObject.getFile().getPath());
			Map<String, Object> params = null;
			
			// []
			emailService.sendEmailAttTemplate(emailInfo.getFromEmail(), arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, params, myFilePath,fileModelBean.getFileName());

			log.debug("SendEmail Controller");
			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setResult(emailInfo);
			sendEmailResult.setSuccess(true);
			sendEmailResult.setResponseCode("200");
			sendEmailResult.setResponseDescription("Delivered successfully");

			EmailLogBean logBean = new EmailLogBean();
			logBean.setParentId(emailInfo.getParentId());
			logBean.setParentModule(emailInfo.getParentModule());
			logBean.setToEmail(emailInfo.getToEmail());
			logBean.setCcEmail(emailInfo.getCcEmail());
			logBean.setSubjectEmail(emailInfo.getSubjectEmail());
			logBean.setBodyEmail(emailInfo.getBodyEmail());
			logBean.setAttachmentId(emailInfo.getAttachmentId());
			logBean.setCreatedBy(getUserId());
			logBean.setUpdatedBy(getUserId());

			if (sendEmailResult.isSuccess()) {

				logBean.setStatusCode("1");
				logBean.setStatusDesc("Successfully");

			//	emailLogService.createEmailLog(logBean);

				return ApiResponse.success(sendEmailResult.getResult());
			} else {

				logBean.setStatusCode("2");
				logBean.setStatusDesc("	Failure");

			//	emailLogService.createEmailLog(logBean);

				sendEmailResult = new ServiceResult<EmailBean>();
				sendEmailResult.setResult(emailInfo);
				sendEmailResult.setSuccess(false);
				sendEmailResult.setResponseCode("500");
				sendEmailResult.setResponseDescription("Delivery Failure");

				return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
			}

		} catch (Exception e) {
			EmailLogBean logBean = new EmailLogBean();
			logBean.setParentId(emailInfo.getParentId());
			logBean.setParentModule(emailInfo.getParentModule());
			logBean.setToEmail(emailInfo.getToEmail());
			logBean.setCcEmail(emailInfo.getCcEmail());
			logBean.setSubjectEmail(emailInfo.getSubjectEmail());
			logBean.setBodyEmail(emailInfo.getBodyEmail());
			logBean.setAttachmentId(emailInfo.getAttachmentId());

			logBean.setCreatedBy(getUserId());
			logBean.setUpdatedBy(getUserId());
			logBean.setStatusCode("2");
			logBean.setStatusDesc("	Failure");
			logBean.setDeliverDesc(e.getMessage());
			emailLogService.createEmailLog(logBean);

			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setResult(emailInfo);
			sendEmailResult.setSuccess(false);
			sendEmailResult.setResponseDescription(e.getMessage());
			sendEmailResult.setResponseCode("500");

			e.printStackTrace();

			return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
		}

	}

	@PostMapping(value = "/sendEmailWithMultiAtt", produces = "application/json")
	public ApiResponse<EmailBean> sendEmailWithMultiAtt(@RequestBody ApiRequest<EmailBean> request) {

		EmailBean emailInfo = new EmailBean();

		try {

			emailInfo = request.getData();

			String emailCcReq = "";
			String subjectReq = "";
			String messageDesc = "";
			String fromName = "JLO CRM System";
			String emailTo = "";
			String emailCc = "";
			
			if(emailInfo.getCcEmail() != null && !emailInfo.getCcEmail().trim().isEmpty()) {
				emailCcReq = emailInfo.getCcEmail();
			}
			
			if(emailInfo.getSubjectEmail() != null && !emailInfo.getSubjectEmail().trim().isEmpty()) {
				subjectReq = emailInfo.getSubjectEmail();
			}
			
			if(emailInfo.getBodyEmail() != null && !emailInfo.getBodyEmail().trim().isEmpty()) {
				messageDesc = emailInfo.getBodyEmail();
			}
			
			String templateName = EMAIL_TEMPLATE_ACTIVITY_NOTIC;
			
			 
			emailTo = emailInfo.getToEmail().replaceAll("\\s", "");
			String[] arrayEmailTo = emailTo.replaceAll(";", ",").split(",");

			emailCc = emailCcReq.replaceAll("\\s", "");
			String[] arrayEmailCc = emailCc.replaceAll(";", ",").split(",");
			String[] arrEmailBcc = { null };

			List<FileModelBean> fileListModelBean = new ArrayList<FileModelBean>();
			ServiceResult<List<FileModelBean>> serviceResult =  attachmentMgntService.getAttachmentListByRefId(Long.valueOf(emailInfo.getParentId()));

			fileListModelBean = serviceResult.getResult();
			
			log.debug("fileListModelBean size: "+fileListModelBean.size());
			Map<String, Object> params = null;
			
			
			emailService.sendEmailMultiAttTemplate(emailInfo.getFromEmail(), arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, params, fileListModelBean);

			log.debug("SendEmail Controller");
			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setResult(emailInfo);
			sendEmailResult.setSuccess(true);
			sendEmailResult.setResponseCode("200");
			sendEmailResult.setResponseDescription("Delivered successfully");

			EmailLogBean logBean = new EmailLogBean();
			logBean.setParentId(emailInfo.getParentId());
			logBean.setParentModule(emailInfo.getParentModule());
			logBean.setToEmail(emailInfo.getToEmail());
			logBean.setCcEmail(emailInfo.getCcEmail());
			logBean.setSubjectEmail(emailInfo.getSubjectEmail());
			logBean.setBodyEmail(emailInfo.getBodyEmail());
			logBean.setAttachmentId(emailInfo.getAttachmentId());
			logBean.setCreatedBy(getUserId());
			logBean.setUpdatedBy(getUserId());

			if (sendEmailResult.isSuccess()) {

				logBean.setStatusCode("1");
				logBean.setStatusDesc("Successfully");

				emailLogService.createEmailLog(logBean);

				return ApiResponse.success(sendEmailResult.getResult());
			} else {

				logBean.setStatusCode("2");
				logBean.setStatusDesc("	Failure");

				emailLogService.createEmailLog(logBean);

				sendEmailResult = new ServiceResult<EmailBean>();
				sendEmailResult.setResult(emailInfo);
				sendEmailResult.setSuccess(false);
				sendEmailResult.setResponseCode("500");
				sendEmailResult.setResponseDescription("Delivery Failure");

				return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
			}

		} catch (Exception e) {
			
			EmailLogBean logBean = new EmailLogBean();
			logBean.setParentId(emailInfo.getParentId());
			logBean.setParentModule(emailInfo.getParentModule());
			logBean.setToEmail(emailInfo.getToEmail());
			logBean.setCcEmail(emailInfo.getCcEmail());
			logBean.setSubjectEmail(emailInfo.getSubjectEmail());
			logBean.setBodyEmail(emailInfo.getBodyEmail());
			logBean.setAttachmentId(emailInfo.getAttachmentId());

			logBean.setCreatedBy(getUserId());
			logBean.setUpdatedBy(getUserId());
			logBean.setStatusCode("2");
			logBean.setStatusDesc("	Failure");
			logBean.setDeliverDesc(e.getMessage());
			emailLogService.createEmailLog(logBean);

			ServiceResult<EmailBean> sendEmailResult = new ServiceResult<EmailBean>();
			sendEmailResult.setResult(emailInfo);
			sendEmailResult.setSuccess(false);
			sendEmailResult.setResponseDescription(e.getMessage());
			sendEmailResult.setResponseCode("500");

			e.printStackTrace();
			
			return ApiResponse.fail(sendEmailResult.getResponseCode(), sendEmailResult.getResponseDescription());
		}

	}

}
