package th.co.locus.jlo.business.qustionnaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;


@Slf4j
@RestController
@RequestMapping("api/questionnaire")
public class QustionnaireController extends BaseController {
	
	
	@Autowired
	private QustionnaireService qtnService;
	
	@WritePermission
    @PostMapping(value = "/createHeaderQuestionnaire", produces = "application/json")
    public ApiResponse<QuestionnaireHeaderModelBean> createHeaderQuestionnaire(@RequestBody ApiPageRequest<QuestionnaireHeaderModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		
		
		QuestionnaireHeaderModelBean bean = request.getData();
		 
		bean.setBuId(getBuId());
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());
		
		log.debug("createSr> "+bean);
		log.info("createSr> "+bean);
		
		ServiceResult<QuestionnaireHeaderModelBean> serviceResult = qtnService.createHeaderQuestionnaire(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
    }
	
	@WritePermission
    @PostMapping(value = "/updateHeaderQuestionnaire", produces = "application/json")
    public ApiResponse<QuestionnaireHeaderModelBean> updateHeaderQuestionnaire(@RequestBody ApiPageRequest<QuestionnaireHeaderModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		ServiceResult<QuestionnaireHeaderModelBean> serviceResult = qtnService.updateHeaderQuestionnaire(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
    }
	

}
