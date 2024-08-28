package th.co.locus.jlo.business.qustionnaire;

import java.io.File;
import java.util.List;

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
import th.co.locus.jlo.business.qustionnaire.bean.QuesionnaireRepondentResponseModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireAnswerModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireResponsesModelBean;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.common.bean.Page;


@Slf4j
@RestController
@RequestMapping("api/questionnaire")
public class QustionnaireController extends BaseController {
	
	@Value("${attachment.path.questionnaire_image}")
	private String questionnaireImagePath;
	
	@Autowired
	private QustionnaireService qtnService;
	
	@Autowired
	private FileService fileService;
	
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
	
	@PostMapping(value="/createQuestionnaireResponse",produces = "application/json")
	public ApiResponse<QuesionnaireRepondentResponseModelBean> createQuestionnaireResponse(@RequestBody ApiRequest<QuesionnaireRepondentResponseModelBean> request) {
		try {
			ServiceResult<QuesionnaireRepondentResponseModelBean> resultService=this.qtnService.createQuestionnaireResponse(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getquestionnairerepondentList",produces = "application/json")
	public ApiPageResponse<List<QuestionnaireRepondentsModelBean>> getQuestionnaireRepondentList(@RequestBody ApiPageRequest<QuestionnaireHeaderModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<QuestionnaireRepondentsModelBean>> resultServiceResult=this.qtnService.getQuestionnaireRepondentsList(request.getData(), pageRequest);
			if(resultServiceResult.isSuccess()) {
				return ApiPageResponse.success(resultServiceResult.getResult().getContent(),resultServiceResult.getResult().getTotalElements());
			}else {
				return ApiPageResponse.fail(resultServiceResult.getResponseCode(),resultServiceResult.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getquestionnaireresponselist",produces = "application/json")
	public ApiPageResponse<List<QuestionnaireResponsesModelBean>> getQuestionnaireResponseList(@RequestBody ApiPageRequest<QuestionnaireRepondentsModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<QuestionnaireResponsesModelBean>> resultService=this.qtnService.getQuestionnaireResponseList(request.getData(),pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}else {
				return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@WritePermission
	@PostMapping(value = "/uploadImg")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		String message = "";
		try {
			String fileName = id + "_"+System.currentTimeMillis()+""+ CommonUtil.getFileExtension(file);
			fileService.saveFile(file, questionnaireImagePath ,fileName);
			this.qtnService.updateQuestionnaireQuestionImg(id,fileName);
			return ResponseEntity.status(HttpStatus.OK).body(fileName);
		}catch(Exception ex) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@GetMapping(value="/questionnaire_image/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getQuestionnaireImage(@PathVariable String fileName) {
		Resource file = fileService.loadFile(questionnaireImagePath + File.separator + fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	

}
