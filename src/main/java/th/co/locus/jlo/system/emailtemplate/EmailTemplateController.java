package th.co.locus.jlo.system.emailtemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import th.co.locus.jlo.system.emailtemplate.bean.EmailTemplateModelBean;
import th.co.locus.jlo.system.file.FileService;

@RestController
@RequestMapping("api/email-template")
public class EmailTemplateController extends BaseController {

	@Value("${attachment.path.att}")
	private String attachmentPath;

	@Autowired
	private EmailTemplateService emailTemplateService;

	@Autowired
	private FileService fileService;

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

	@WritePermission
	@PostMapping(value = "/saveEmailTemplate", produces = "application/json")
	public ApiResponse<EmailTemplateModelBean> saveEmailTemplate(@RequestBody ApiRequest<EmailTemplateModelBean> request) {

		ServiceResult<EmailTemplateModelBean> serviceResult;		
		EmailTemplateModelBean emailtemplateBean = new EmailTemplateModelBean();
		CommonUtil.nullifyObject(emailtemplateBean);
		emailtemplateBean = request.getData();
		emailtemplateBean.setCreatedBy(getUserId());
		emailtemplateBean.setUpdatedBy(getUserId());
		emailtemplateBean.setBuId(getBuId());

		if (emailtemplateBean.getId()>0) {
			serviceResult = emailTemplateService.updateEmailTemplate(request.getData());
		} else {
			serviceResult = emailTemplateService.createEmailTemplate(request.getData());
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
}
