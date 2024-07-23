package th.co.locus.jlo.system.emailtemplate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.kb.KbService;
import th.co.locus.jlo.kb.modelbean.KbDocumentModelBean;
import th.co.locus.jlo.system.emailtemplate.bean.EmailTemplateModelBean;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Slf4j
@RestController
@RequestMapping("api/email-template")
public class EmailTemplateController extends BaseController {

	@Value("${attachment.path.att}")
	private String attachmentPath;

	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@Value("${attachment.path.email.att}")
	private String emailPath;
	
	@Autowired
	private FileService fileService;
	
	@Value("${attachment.path.email.template_image}")
	private String emailImagePath;
	
	@ReadPermission
	@PostMapping(value = "/getEmailTemplateList", produces = "application/json")
	public ApiPageResponse<List<EmailTemplateModelBean>> getEmailTemplateList(
			@RequestBody ApiPageRequest<EmailTemplateModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<EmailTemplateModelBean>> serviceResult = emailTemplateService
				.getEmailTemplateList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
		

	@ReadPermission
	@PostMapping(value = "/getEmailTemplateByModule", produces = "application/json")
	public ApiResponse<EmailTemplateModelBean> getEmailTemplateByModule(@RequestBody ApiPageRequest<EmailTemplateModelBean> request) {
		log.info("getEmailTemplateByModule Criteria : "+request.getData());
		EmailTemplateModelBean emailTemplateData = request.getData();
		emailTemplateData.setBuId(getBuId());
		ServiceResult<EmailTemplateModelBean> serviceResult = emailTemplateService.getEmailTemplateByModule(emailTemplateData);
		if (serviceResult.isSuccess()) {
			EmailTemplateModelBean emailTemplateDataList = serviceResult.getResult();
			log.info("emailTemplateDataList : "+emailTemplateDataList);

			return ApiResponse.success(emailTemplateDataList);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	 
	@PostMapping(value = "/saveEmailTemplate")
	public ApiResponse<EmailTemplateModelBean> saveEmailTemplate(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "templateName") String templateName, 
			@RequestParam("attId") String attId,
			@RequestParam("statusCd") String statusCd, 
			@RequestParam("description") String description,
			@RequestParam("templateHtmlCode") String templateHtmlCode,
			@RequestParam("module") String module) throws IOException {

		EmailTemplateModelBean emailTemplateBean = new EmailTemplateModelBean();
		
		emailTemplateBean.setId(StringUtils.isNotEmpty(id) ? Long.valueOf(id) : 0);
		emailTemplateBean.setAttId(StringUtils.isNotEmpty(attId) ? Long.valueOf(attId) : 0);
		
		emailTemplateBean.setTemplateName(templateName);
		emailTemplateBean.setStatusCd(statusCd);
		emailTemplateBean.setDescription(description);
		emailTemplateBean.setTemplateHtmlCode(templateHtmlCode);
		emailTemplateBean.setModule(module);
		emailTemplateBean.setBuId(getBuId());
		emailTemplateBean.setCreatedBy(getUserId());
		emailTemplateBean.setUpdatedBy(getUserId());

		CommonUtil.nullifyObject(emailTemplateBean);

		FileModelBean fileBean = null;
		if (file != null) {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String fileName = "email_template_"+timeStamp+ CommonUtil.getFileExtension(file);
			fileBean = new FileModelBean();
			fileBean.setFilePath(emailPath + File.separator);
			fileBean.setFileExtension(CommonUtil.getFileExtension(file));
			fileBean.setFileName(fileName);
			fileBean.setFileSize(file.getSize());
			fileBean.setCreatedBy(getUserId());
			fileBean.setUpdatedBy(getUserId());
		}
		
		ServiceResult<EmailTemplateModelBean> serviceResult; 		
		
		if (emailTemplateBean.getId() > 0) {
			serviceResult = emailTemplateService.updateEmailTemplate(emailTemplateBean,fileBean,file);
		} else {
			serviceResult = emailTemplateService.createEmailTemplate(emailTemplateBean,fileBean,file);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@PostMapping(value = "/deleteEmailTemplate", produces = "application/json")
	public ApiResponse<Integer> deleteEmailTemplate(@RequestBody ApiRequest<EmailTemplateModelBean> request) {
		return ApiResponse.success(emailTemplateService.deleteEmailTemplate(request.getData()).getResult());
	}
	
	
	@WritePermission
	@PostMapping(value = "/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("templateId") Long templateId) {
		String message = "";
	 
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String fileName = templateId + "_"+timeStamp+ CommonUtil.getFileExtension(file);
			fileService.saveFile(file, emailImagePath, fileName);
			
			//customerService.updateCustomerProfileImage(fileName, templateId);
			
			return ResponseEntity.status(HttpStatus.OK).body(fileName);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@GetMapping(value = "/email_template_image/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
		log.info("getProfileImage: "+emailImagePath + File.separator + fileName);
		Resource file = fileService.loadFile(emailImagePath + File.separator + fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
}
