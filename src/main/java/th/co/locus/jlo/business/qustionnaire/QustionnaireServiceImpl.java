package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.qustionnaire.bean.QuesionnaireRepondentResponseModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireAnswerModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireResponsesModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

@Log4j2
@Service
public class QustionnaireServiceImpl extends BaseService implements QustionnaireService {

	@Override
	public ServiceResult<Page<QuestionnaireHeaderModelBean>> getHeaderQuestionaire(QuestionnaireHeaderModelBean bean,PageRequest page) {
		try {
			return success(commonDao.selectPage("questionnaire.getHeaderQuestionaireList", bean, page));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}
	
	@Override
	public ServiceResult<QuestionnaireHeaderModelBean> getQuestionnaireById(QuestionnaireHeaderModelBean bean) {
		try {
			QuestionnaireHeaderModelBean headerQtn=commonDao.selectOne("questionnaire.findQuestionnaireById", bean);
			if(headerQtn!=null) {
				headerQtn.setQuestionnaireList(commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",bean.getId())));
			}
			return success(headerQtn);
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<QuestionnaireHeaderModelBean> createHeaderQuestionnaire(QuestionnaireHeaderModelBean bean) {
		try {
			int result = commonDao.update("questionnaire.createHeaderQuestionnaire", bean);
			if (result > 0) {
				return success(commonDao.selectOne("questionnaire.findQuestionnaireById", bean));
			}
			return fail("500","Unable to create because something wrong.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
 
	}

	@Override
	public ServiceResult<QuestionnaireHeaderModelBean> updateHeaderQuestionnaire(QuestionnaireHeaderModelBean bean) {
		try {
			int result = commonDao.update("questionnaire.updateHeaderQuestionnaire", bean);
			if (result > 0) {
				return success(commonDao.selectOne("questionnaire.findQuestionnaireById", bean));
			}
			return fail("500","Unable to edit because data not found.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<QuestionnaireQuestionModelBean>> getQuestionnaireQuestionList(QuestionnaireQuestionModelBean bean) {
		try {
			return success(commonDao.selectList("questionnaire.getQuestionnaireQuestionList", bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuestionnaireQuestionModelBean> getQuestionnaireQuestionDetail(QuestionnaireQuestionModelBean bean) {
		try {
			return success(commonDao.selectOne("questionnaire.getQuestionnairequestionDetail",bean));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuestionnaireQuestionModelBean> createQuestionnaireQuestion(QuestionnaireQuestionModelBean bean) {
		try {
			int result=commonDao.insert("questionnaire.createQuestionnaireQuestion", bean);
			if(result>0) {
				String[] options = bean.getOptions().split(" , ");
				List<String> optionList = Arrays.asList(options);
				commonDao.delete("questionnaire.deleteQuestionnaireAnswerOption", Map.of("questionId",bean.getId()));
			    for (String item : optionList) {
			    	QuestionnaireAnswerModelBean answer=new QuestionnaireAnswerModelBean();
			    	answer.setQuestionId(bean.getId());
			    	answer.setAnswer(item);
			    	answer.setStatusCd("Y");
			    	answer.setCreatedBy(bean.getCreatedBy());
			    	answer.setUpdatedBy(bean.getUpdatedBy());
			    	commonDao.insert("questionnaire.createQuestionaireAnswerMaster", answer);
		        }
				return success(commonDao.selectOne("questionnaire.getQuestionnairequestionDetail", bean));
			}
			return fail("500","Unable to create because something wrong.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuestionnaireQuestionModelBean> updateQuestionnaireQuestion(QuestionnaireQuestionModelBean bean) {
		try {
			int result=commonDao.update("questionnaire.updateQuestionnaireQuestion", bean);
			if(result>0) {
				String[] options = bean.getOptions().split(" , ");
				List<String> optionList = Arrays.asList(options);
				commonDao.delete("questionnaire.deleteQuestionnaireAnswerOption", Map.of("questionId",bean.getId()));
			    for (String item : optionList) {
			    	QuestionnaireAnswerModelBean answer=new QuestionnaireAnswerModelBean();
			    	answer.setQuestionId(bean.getId());
			    	answer.setAnswer(item);
			    	answer.setStatusCd("Y");
			    	answer.setCreatedBy(bean.getUpdatedBy());
			    	answer.setUpdatedBy(bean.getUpdatedBy());
			    	commonDao.insert("questionnaire.createQuestionaireAnswerMaster", answer);
		        }
				return success(commonDao.selectOne("questionnaire.getQuestionnairequestionDetail", bean));
			}
			return fail("500","Unable to edit because data not found.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuesionnaireRepondentResponseModelBean> createQuestionnaireResponse(QuesionnaireRepondentResponseModelBean bean) {
		try {
			log.info("Data: "+bean.toString());
			int resultRespodent=commonDao.insert("questionnaire.createQuesuionnaireRespondent",bean.getRespodent());
			if(resultRespodent>0) {
				for (QuestionnaireResponsesModelBean response : bean.getResponses()) {
					response.setRespondentId(bean.getRespodent().getRespondentId());
					response.setQuestionnaireHeaderId(bean.getRespodent().getQuestionnaireHeaderId());
					log.info("Data response: "+response.toString());
					commonDao.insert("questionnaire.createQuestionnaireResponse", response);
				}
				return this.getQuestionnaireResponse(bean);
			}
			return fail("500","Unable to create because something wrong.");
			
		}catch(Exception ex) {
			log.error("Error : "+ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuesionnaireRepondentResponseModelBean> getQuestionnaireResponse(QuesionnaireRepondentResponseModelBean bean) {
		try {
			QuestionnaireRepondentsModelBean respondent=commonDao.selectOne("questionnaire.getquestionaireRepondentDetail", bean.getRespodent());
			if(respondent!=null) {
				List<QuestionnaireResponsesModelBean> responseList=commonDao.selectList("questionnaire.getQuestionnaireResponseList", respondent);
				QuesionnaireRepondentResponseModelBean newRespondent=new QuesionnaireRepondentResponseModelBean();
				newRespondent.setRespodent(respondent);
				newRespondent.setResponses(responseList);
				return success(newRespondent);
			}
			return fail("500","Unable to create because something wrong.");
			
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}

	}

	@Override
	public ServiceResult<Page<QuestionnaireRepondentsModelBean>> getQuestionnaireRepondentsList(QuestionnaireHeaderModelBean bean, PageRequest page) {
		try {
			return success(commonDao.selectPage("questionnaire.getquestionaireRepondentList", Map.of("id",bean.getId()), page));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<QuestionnaireResponsesModelBean>> getQuestionnaireResponseList(QuestionnaireRepondentsModelBean bean, PageRequest page) {
		try {
			return success(commonDao.selectPage("questionnaire.getQuestionnaireResponseList", bean, page));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuestionnaireQuestionModelBean> updateQuestionnaireQuestionImg(Long questionId,String imageUrl) {
		try {
			int result=commonDao.update("questionnaire.updateQuestionnaireQuestionImage", Map.of("imegeUrl",imageUrl,"id",questionId));
			if(result>0) {
				return success(commonDao.selectOne("questionnaire.getQuestionnairequestionDetail", Map.of("id",questionId)));
			}else {
				return fail("500","Unable to update because something wrong.");
			}
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}



	
}
