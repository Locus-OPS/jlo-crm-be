package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
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
			ServiceResult<Page<QuestionnaireRepondentsModelBean>> resultService=this.qtnDashboardService.getRespondent(request.getData().getQuestionnaireHeaderId(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
}
