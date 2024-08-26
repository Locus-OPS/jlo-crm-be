package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireAnswerModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.common.bean.Page;


@Slf4j
@RestController
@RequestMapping("api/questionnaire")
public class QustionnaireController extends BaseController {
	
	
	@Autowired
	private QustionnaireService qtnService;
	
//	@WritePermission
    @PostMapping(value = "/createHeaderQuestionnaire", produces = "application/json")
    public ApiResponse<QuestionnaireHeaderModelBean> createHeaderQuestionnaire(@RequestBody ApiRequest<QuestionnaireHeaderModelBean> request) {
		
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
	
	
	@PostMapping(value="/updateHeaderQuestionnaire",produces = "application/json")
	public ApiResponse<QuestionnaireHeaderModelBean> updateHeaderQuestionaire(@RequestBody ApiRequest<QuestionnaireHeaderModelBean> request) {
		try {
			request.getData().setBuId(getBuId());
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			log.info("Hello"+request.getData().toString());
			ServiceResult<QuestionnaireHeaderModelBean> serviceResult=this.qtnService.updateHeaderQuestionnaire(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}else {
				return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
    
    
	
	@PostMapping(value="/getheaderquestionairelist",produces = "application/json")
	public ApiPageResponse<List<QuestionnaireHeaderModelBean>> getHeaderQuestionaireList(@RequestBody ApiPageRequest<QuestionnaireHeaderModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<QuestionnaireHeaderModelBean>> resultService=this.qtnService.getHeaderQuestionaire(request.getData(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}else {
				return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getheaderquestionnairedetail",produces = "application/json")
	public ApiResponse<QuestionnaireHeaderModelBean> getHeaderQuestionnaireDetail(@RequestBody ApiRequest<QuestionnaireHeaderModelBean> request) {
		try {
			ServiceResult<QuestionnaireHeaderModelBean> resultService=this.qtnService.getQuestionnaireById(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
//	############## Questionaire Quesution Section ##########
	
	@PostMapping(value="/getquestionairequestionlist",produces = "application/json")
	public ApiResponse<List<QuestionnaireQuestionModelBean>> getQuestionnaireQuestionList(@RequestBody ApiRequest<QuestionnaireQuestionModelBean> request) {
		try {
			ServiceResult<List<QuestionnaireQuestionModelBean>> resultService=this.qtnService.getQuestionnaireQuestionList(request.getData());
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult());
			}else {
				return ApiPageResponse.fail(resultService.getResponseCode(), resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiPageResponse.fail("500", ex.getMessage());
		}
	}
	
	@PostMapping(value="/getquestionnairequestiondetail",produces = "application/json")
	public ApiResponse<QuestionnaireQuestionModelBean> getQuestionnaireQuestionDetail(@RequestBody ApiRequest<QuestionnaireQuestionModelBean> request) {
		try {
			ServiceResult<QuestionnaireQuestionModelBean> resultService=this.qtnService.getQuestionnaireQuestionDetail(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createquestionnairequestion")
	public ApiResponse<QuestionnaireQuestionModelBean> createQuestionnaireQuestion(@RequestBody ApiRequest<QuestionnaireQuestionModelBean> request) {
		try {
			request.getData().setUpdatedBy(getUserId());
			request.getData().setCreatedBy(getUserId());
			ServiceResult<QuestionnaireQuestionModelBean> resultService=this.qtnService.createQuestionnaireQuestion(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/updateQuestionnaireQuestion",produces = "application/json")
	public ApiResponse<QuestionnaireQuestionModelBean> updateQuestionnaireQuestion(@RequestBody ApiRequest<QuestionnaireQuestionModelBean> request) {
		try {
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<QuestionnaireQuestionModelBean> resultService=this.qtnService.updateQuestionnaireQuestion(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createQuestionnaireAnswer")
	public ApiResponse<QuestionnaireAnswerModelBean> createQuestionnaireAnswer(@RequestBody ApiRequest<QuestionnaireAnswerModelBean> request) {
		try {
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<QuestionnaireAnswerModelBean> resultService=this.qtnService.createQuestionnaireAnswer(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}

}
