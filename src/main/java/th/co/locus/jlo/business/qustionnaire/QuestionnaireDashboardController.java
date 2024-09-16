package th.co.locus.jlo.business.qustionnaire;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionSummaryModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardValueModelBean;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.bean.Page;

@Slf4j
@RestController
@RequestMapping("api/questionnairedashboard")
public class QuestionnaireDashboardController extends BaseController {
	@Autowired
	private QuestionnaireDashboardService qtnDashboardService;
	
	@PostMapping(value="/getmaindashboard",produces = "application/json")
	public ApiResponse<QuestionnaireMainDashboardModelBean> getMainDashboard(@RequestBody ApiRequest<QuestionnaireMainDashboardModelBean> request) {
		try {
			ServiceResult<QuestionnaireMainDashboardModelBean> resultService=this.qtnDashboardService.getMainDashboad(request.getData().getHeaderId());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getrespondentlist",produces = "application/json")
	public ApiPageResponse<List<QuestionnaireRepondentsModelBean>> getRespondentList(@RequestBody ApiPageRequest<QuestionnaireRepondentsModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<QuestionnaireRepondentsModelBean>> resultService=this.qtnDashboardService.getRespondent(request.getData(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getquestionresponsesummary",produces = "application/json")
	public ApiResponse<QuestionnaireQuestionSummaryModelBean> getQuestionResponseSummary(@RequestBody ApiRequest<QuestionnaireHeaderModelBean> request) {
		try {
			ServiceResult<QuestionnaireQuestionSummaryModelBean> resultService=this.qtnDashboardService.getQuestionResponseSummary(request.getData().getId());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getsummaryTextList",produces = "application/json")
	public ApiResponse<List<QuestionnaireDashboardValueModelBean>> getSummaryTextList(@RequestBody ApiRequest<QuestionnaireQuestionModelBean> request) {
		try {
			ServiceResult<List<QuestionnaireDashboardValueModelBean>> resultService=this.qtnDashboardService.getQuestionnaireSummaryText(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getresponsedetail",produces = "application/json")
	public ApiResponse<QuestionnaireQuestionSummaryModelBean> getResponseDetail(@RequestBody ApiRequest<QuestionnaireRepondentsModelBean> request) {
		try {
			ServiceResult<QuestionnaireQuestionSummaryModelBean> resultService=this.qtnDashboardService.getQuestionResponseDetail(request.getData().getQuestionnaireHeaderId(), request.getData().getRespondentId());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/exportQuestionnaireResponseSummary",produces = "application/json")
	public ResponseEntity<ByteArrayResource>  exportQuestionnaireResponseSummary(@RequestBody ApiRequest<QuestionnaireQuestionModelBean> request) {
		try {
			ServiceResult<ByteArrayOutputStream> resultService=this.qtnDashboardService.exportQuestionnaireSummary(request.getData());
			if(resultService.isSuccess()) {
	            HttpHeaders header = new HttpHeaders();
	            header.setContentType(new MediaType("application", "force-download"));
	            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=QuestionnaireReport.xlsx");
	            return new ResponseEntity<>(new ByteArrayResource(resultService.getResult().toByteArray()),
	                    header, HttpStatus.CREATED);
	        }
	        return ResponseEntity.noContent().build();
		}catch(Exception ex) {
			log.error(ex.getMessage());
			  return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping(value="/exportquestionnaireresponselist",produces = "application/json")
	public ResponseEntity<ByteArrayResource> exportQuestionnaireResponseList(@RequestBody ApiRequest<QuestionnaireHeaderModelBean> request) {
		try {
			ServiceResult<ByteArrayOutputStream> resultService=this.qtnDashboardService.exportQuestionnaireSummaryList(request.getData());
			if(resultService.isSuccess()) {
	            HttpHeaders header = new HttpHeaders();
	            header.setContentType(new MediaType("application", "force-download"));
	            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=QuestionnaireListReport.xlsx");
	            return new ResponseEntity<>(new ByteArrayResource(resultService.getResult().toByteArray()),
	                    header, HttpStatus.CREATED);
	        }
	        return ResponseEntity.noContent().build();
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.noContent().build();
		}
	}
}
