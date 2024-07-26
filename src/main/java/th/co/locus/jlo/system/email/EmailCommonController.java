/**
 * 
 */
package th.co.locus.jlo.system.email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.attachment.AttachmentMgntService;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
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
	
	@Value("${attachment.path.email.att}")
	private String emailPath;
	
	

	@PostMapping(value = "/sendEmail", produces = "application/json")
	public @ResponseBody ApiResponse<EmailBean> sendEmail(@RequestBody ApiRequest<EmailBean> request) throws Exception {

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
	 
	
	@PostMapping(value = "/sendEmailWithAttachedFile", produces = "application/json")
	public @ResponseBody ApiResponse<EmailBean> sendEmailWithAttachedFile(
				@RequestParam(value = "file", required = false) MultipartFile file,			 
			 	@RequestParam("fromEmail") String fromEmail,
				@RequestParam("toEmail") String toEmail, 
				@RequestParam("ccEmail") String ccEmail,
				@RequestParam("subjectEmail") String subjectEmail, 
				@RequestParam("bodyEmail") String bodyEmail,
				@RequestParam("parentModule") String parentModule) throws IOException {
		
		EmailBean emailInfo = new EmailBean();

		try {

			String emailCcReq = "";
			String subjectReq = "";
			String messageDesc = "";
			String fromName = "JLO CRM System";
			String emailTo = "";
			String emailCc = "";
			
			String[] arrayEmailTo = null;
			String[] arrayEmailCc= null;
			String[] arrEmailBcc = { null };
			Map<String, Object> params = null;
			
			if(toEmail != null && !toEmail.trim().isEmpty()) {
				emailTo = toEmail.replaceAll("\\s", "");
				emailInfo.setToEmail(toEmail);
//				arrayEmailTo = emailTo.replaceAll(";", ",").split(",");
				arrayEmailTo = emailTo.split(",");
			}
			
			if(ccEmail != null && !ccEmail.trim().isEmpty()) {
				emailCcReq = ccEmail;
				emailInfo.setCcEmail(ccEmail);
				emailCc = emailCcReq.replaceAll("\\s", "");
//				arrayEmailCc = emailCc.replaceAll(";", ",").split(",");
				arrayEmailCc = emailCc.split(",");
			}
			
			if(subjectEmail != null && !subjectEmail.trim().isEmpty()) {
				subjectReq = subjectEmail;
				emailInfo.setSubjectEmail(subjectEmail);				
			}
			
			if(bodyEmail != null && !bodyEmail.trim().isEmpty()) {
				messageDesc = bodyEmail;
				emailInfo.setBodyEmail(messageDesc);
			}
					 
			String templateName = EMAIL_TEMPLATE_ACTIVITY_NOTIC;
			
			log.info("fromEmail "+fromEmail);
			log.info("arrayEmailTo "+arrayEmailTo);
			log.info("arrayEmailCc "+arrayEmailCc);
			log.info("arrEmailBcc "+arrEmailBcc);
			log.info("subjectReq "+subjectReq);
			
			if (file != null) {
				File fileAtt = convert(file);				
				FileModelBean fileBean = null;
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				String fileName = "file_att_email_"+timeStamp+ CommonUtil.getFileExtension(file);
				fileBean = new FileModelBean();
				fileBean.setFilePath(emailPath + File.separator);
				fileBean.setFileExtension(CommonUtil.getFileExtension(file));
				fileBean.setFileName(fileName);
				fileBean.setFileSize(file.getSize());
				fileBean.setCreatedBy(getUserId());
				fileBean.setUpdatedBy(getUserId());
				
				fileBean = fileService.createAttachment(fileBean).getResult(); 
				fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
				
				emailService.sendEmailAttTemplate(fromEmail, arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, params, fileAtt,fileAtt.getName());
			}else {
				emailService.sendEmailTemplate(fromEmail, arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, "",null);
			}
			
			
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
	
	
	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
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
			File myFile = new File(fileObject.getFile().getPath());
			Map<String, Object> params = null;
			
			// []
			emailService.sendEmailAttTemplate(emailInfo.getFromEmail(), arrayEmailTo, arrayEmailCc, arrEmailBcc, subjectReq, messageDesc, fromName, params, myFile,fileModelBean.getFileName());

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
