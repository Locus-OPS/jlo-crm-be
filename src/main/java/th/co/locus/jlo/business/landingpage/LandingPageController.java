/**
 * 
 */
package th.co.locus.jlo.business.landingpage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.business.landingpage.bean.LandingPageModelBean;
import th.co.locus.jlo.business.landingpage.bean.SearchLandingPageModelBean;
import th.co.locus.jlo.business.qustionnaire.QustionnaireService;
import th.co.locus.jlo.business.qustionnaire.bean.QuesionnaireRepondentResponseModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.smartlink.SmartLinkController;
import th.co.locus.jlo.business.smartlink.SmartLinkService;
import th.co.locus.jlo.business.smartlink.bean.SmartLinkModelBean;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.codebook.CodebookService;
import th.co.locus.jlo.system.internationalization.InternationalizationService;

/**
 * 
 */
@Slf4j
@RestController
@RequestMapping("api/landing")
public class LandingPageController {
	@Autowired
	private CodebookService codebookService;

	@Autowired
	private InternationalizationService internationalizationService;

	@Autowired
	private LandingPageService landingPageService;

	@Autowired
	private SmartLinkService smartLinkService;

	@Autowired
	private QustionnaireService questionnaireService;
	
	@PostMapping(value = "/getLandingQuestionnaireMaster1", produces = "application/json")
	public ApiResponse<ConsultingModelBean> getLandingQuestionnaireMaster1(
			@RequestBody ApiRequest<Map<String, Object>> request) {
		log.info("1##########getLandingQuestionnaireMaster#############");
		log.debug("data Has key : "+request.getData());
		log.debug("has key : "+request.getData().get("hasKey"));
		
		return null;
	}
	
	@PostMapping(value = "/getLandingQuestionnaireMaster", produces = "application/json")
	public ApiResponse<QuestionnaireHeaderModelBean> getLandingQuestionnaireMaster
			(@RequestBody ApiRequest<SearchLandingPageModelBean> request) 		
					throws ParseException {
		System.out.println("1##########getLandingQuestionnaireMaster#############");
		log.info("1##########getLandingQuestionnaireMaster#############");
		log.debug("data Has key : "+request.getData().getHasKey());
		SearchLandingPageModelBean bean = request.getData();
		log.debug("Haskey : "+bean.getHasKey());
		System.out.println("2##########getLandingQuestionnaireMaster#############");
		log.info("2##########getLandingQuestionnaireMaster#############");
		//LandingPageModelBean landingPageModelBean = new LandingPageModelBean();
		//QuestionnaireHeaderModelBean qHeaderBean = new QuestionnaireHeaderModelBean();
		ServiceResult<SmartLinkModelBean> serviceResult = smartLinkService.getSmartLinkByHashKey(bean.getHasKey());
		System.out.println("3##########getLandingQuestionnaireMaster#############");
		if (serviceResult.isSuccess()) {
			SmartLinkModelBean smartLinkModelBean = serviceResult.getResult();
			Date expireDateLink = smartLinkModelBean.getExpireDate();
			//qHeaderBean.setId(smartLinkModelBean.getHeaderId());
			
			if(checkExpiryDate(expireDateLink)) {
				String responseCode="401";
				String responseDescription="Authorization Required : [Link has been expired]";				
				return ApiResponse.fail(responseCode, responseDescription);
			}
		}
		QuestionnaireHeaderModelBean qHeader=new QuestionnaireHeaderModelBean();
		qHeader.setId(serviceResult.getResult().getHeaderId());
		ServiceResult<QuestionnaireHeaderModelBean> resultQusetionnaireHeader=this.questionnaireService.getQuestionnaireById(qHeader);
		
		if(resultQusetionnaireHeader.isSuccess()) {
			return ApiResponse.success(resultQusetionnaireHeader.getResult());
		}
		
		return ApiResponse.fail("500",resultQusetionnaireHeader.getResponseDescription());

	}
	
	//สร้างแบบสอบถามจากหน้าจอ Landing Page
	@PostMapping(value="/createquestionnaire",produces = "application/json")
	public ApiResponse<QuesionnaireRepondentResponseModelBean> createQuestionnaire(@RequestBody ApiRequest<QuesionnaireRepondentResponseModelBean> request) {
		try {
			ServiceResult<QuesionnaireRepondentResponseModelBean> resultService=this.questionnaireService.createQuestionnaireResponse(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}else {
				return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
			}
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}

	private static boolean checkExpiryDate(Date input) throws ParseException {

//	 	String input = "2024-08-22 16:50:10";//"2024-08-21 17:46:58";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setLenient(false);
		Date expiry = input;// simpleDateFormat.parse(input);

		boolean expired = expiry.before(new Date());
		if (expired == true) {
			System.out.println("This card has already expired");
			return true;

		} else {
			System.out.println("This card has not expired");
			return false;
		}

	}

}
