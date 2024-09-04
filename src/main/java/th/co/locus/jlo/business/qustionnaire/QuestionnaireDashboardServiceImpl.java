package th.co.locus.jlo.business.qustionnaire;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionSummaryModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardGenderGroupModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardValueModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
public class QuestionnaireDashboardServiceImpl extends BaseService  implements QuestionnaireDashboardService {

	@Override
	public ServiceResult<QuestionnaireMainDashboardModelBean> getMainDashboad(Long headerId) {
		try {
			QuestionnaireMainDashboardModelBean mainDashboard=new QuestionnaireMainDashboardModelBean();
			List<QuestionnaireDashboardValueModelBean> genderList = new ArrayList<QuestionnaireDashboardValueModelBean>();
			QuestionnaireDashboardGenderGroupModelBean genderGroup=commonDao.selectOne("questionnaire-dashboard.getQuestionnaireGenderGroup", Map.of("headerId",headerId));
			if(genderGroup!=null) {
				mainDashboard.setTotalRespondent(genderGroup.getTotalRespondent());
				genderList.add(new QuestionnaireDashboardValueModelBean("Male", genderGroup.getTotalMale().toString()));
				genderList.add(new QuestionnaireDashboardValueModelBean("Female", genderGroup.getTotalFemale().toString()));
				genderList.add(new QuestionnaireDashboardValueModelBean("Others", genderGroup.getTotalOthers().toString()));
				mainDashboard.setGenderGroup(genderList);
			}
			List<QuestionnaireDashboardValueModelBean> ageGroupList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireAgeGroupList", Map.of("headerId",headerId));
			if(ageGroupList.size()>0) {
				mainDashboard.setAgeGroup(ageGroupList);
			}
			
			return success(mainDashboard);
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<QuestionnaireRepondentsModelBean>> getRespondent(Long headerId,PageRequest page) {
		try {
			Page<QuestionnaireRepondentsModelBean> respondentList=commonDao.selectPage("questionnaire-dashboard.getQuestionnaireRespondentList", headerId, page);
			return success(respondentList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<QuestionnaireDashboardValueModelBean>> getQuestionnaireSummaryText(QuestionnaireQuestionModelBean bean) {
		try {
			List<QuestionnaireDashboardValueModelBean> summaryTextList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryTextList", Map.of("headerId",bean.getHeaderId(),"questionId",bean.getId(),"isShortList","Y"));
			return success(summaryTextList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuestionnaireQuestionSummaryModelBean> getQuestionResponseSummary(Long headerId) {
		try {
			log.info("Header :: "+headerId.toString());
			QuestionnaireQuestionSummaryModelBean summaryList=new QuestionnaireQuestionSummaryModelBean(); 
			List<QuestionnaireQuestionModelBean> questionList=commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",headerId));

			for (QuestionnaireQuestionModelBean question : questionList) {

				if(question.getComponentType().equals("text") || question.getComponentType().equals("textarea")) {
					List<QuestionnaireDashboardValueModelBean> summaryTextList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryTextList", Map.of("headerId",headerId,"questionId",question.getId(),"isShortList","Y"));
					if(summaryTextList.size()>0) {
						question.setSummaryDetail(summaryTextList);
					}
				}
				else if(question.getComponentType().equals("date")) {
					List<QuestionnaireDashboardValueModelBean> summaryDateList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryDateList", Map.of("headerId",headerId,"questionId",question.getId(),"isShortList","Y"));
					if(summaryDateList.size()>0) {
						question.setSummaryDetail(summaryDateList);
					}
				}
				else if(question.getComponentType().equals("radio") || question.getComponentType().equals("select") || question.getComponentType().equals("checkbox")) {
					List<QuestionnaireDashboardValueModelBean> summaryDateList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryRadioSelectList", Map.of("headerId",headerId,"questionId",question.getId()));
					if(summaryDateList.size()>0) {
						question.setSummaryDetail(summaryDateList);
					}
				}
				
			}
			summaryList.setQuestion(questionList);
			return success(summaryList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<QuestionnaireQuestionSummaryModelBean> getQuestionResponseDetail(Long headerId,Long respondentId) {
		try {
			QuestionnaireQuestionSummaryModelBean summaryList=new QuestionnaireQuestionSummaryModelBean(); 
			List<QuestionnaireQuestionModelBean> questionList=commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",headerId));
			for (QuestionnaireQuestionModelBean question : questionList) {

				if(question.getComponentType().equals("date")) {
					List<QuestionnaireDashboardValueModelBean> responseDateDetail=commonDao.selectList("questionnaire-dashboard.getQuestionnaireResponseDate", Map.of("headerId",headerId,"questionId",question.getId(),"respondentId",respondentId));
					question.setResponseDetail(responseDateDetail);
				}else {
					List<QuestionnaireDashboardValueModelBean> responseTextDetail=commonDao.selectList("questionnaire-dashboard.getQuestionnaireResponseText", Map.of("headerId",headerId,"questionId",question.getId(),"respondentId",respondentId));
					question.setResponseDetail(responseTextDetail);
				}
				
			}
			summaryList.setQuestion(questionList);
			return success(summaryList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public void exportQuestionnaireSummary(QuestionnaireQuestionModelBean bean) {
		try {
			List<QuestionnaireDashboardValueModelBean> summaryList = new ArrayList<QuestionnaireDashboardValueModelBean>();
			if(bean.getComponentType().equals("text") || bean.getComponentType().equals("textarea")) {
				summaryList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryTextList", Map.of("headerId",bean.getHeaderId(),"questionId",bean.getQuestion(),"isShortList","N"));
			}else if(bean.getComponentType().equals("date")) {
				summaryList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryDateList", Map.of("headerId",bean.getHeaderId(),"questionId",bean.getQuestion(),"isShortList","N"));
			}
			
			for (QuestionnaireDashboardValueModelBean response : summaryList) {
				
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
		
	}

	@Override
	public void exportQuestionnaireSummaryList(QuestionnaireQuestionModelBean bean) {
		try {
			//ดึงคำถาม
			List<QuestionnaireQuestionModelBean> questionList=commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",bean.getHeaderId()));
			//ดึงผู้ตอบคำถาม
			List<QuestionnaireRepondentsModelBean> respondentList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireRespondentList", Map.of("headerId",bean.getHeaderId()));
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
		
	}
	
	

}
