package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireAnswerModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import java.util.Map;
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
				return success(commonDao.selectOne("questionnaire.getQuestionnairequestionDetail", bean));
			}
			return fail("500","Unable to edit because data not found.");
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<QuestionnaireAnswerModelBean>> getQuestionnaireAnswerList(QuestionnaireAnswerModelBean bean, PageRequest page) {
		try {
			return success(commonDao.selectPage("questionnaire.getQuestionnaireAnswerList", bean, page));
		}
		catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<QuestionnaireAnswerModelBean>> getQuestionnaireAnswerResult(QuestionnaireAnswerModelBean bean, PageRequest page) {
		try {
			return success(commonDao.selectPage("questionnaire.getQuestionnaireAnswerResultList", bean, page));
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<QuestionnaireAnswerModelBean>> createQuestionnaireAnswer(List<QuestionnaireAnswerModelBean> bean) {
		try {
			long id=0,headerId=0;
			if(bean.size()>0) {
				headerId=bean.get(0).getHeaderId();
			}
			id=commonDao.selectOne("questionnaire.genQuestionnaireAnswerId", Map.of("headerId",headerId));
			id+=1;
			
			for (QuestionnaireAnswerModelBean answer : bean) {
			    try {
			    	answer.setId(id);
			    	answer.setUpdatedBy(0L);
			    	answer.setCreatedBy(0L);
			        commonDao.insert("questionnaire.createQuestionnareAnswer", answer);
			    }catch(Exception ex) {
			    	log.error(ex.getMessage());
			    }
			}
			
			List<QuestionnaireAnswerModelBean> anslist=commonDao.selectList("questionnaire.getQuestionnaireAnswerDetail", bean);
			return success(anslist);
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<List<QuestionnaireAnswerModelBean>> updateQuestionnaireAnswer(List<QuestionnaireAnswerModelBean> bean) {
		try {
			long id=0,headerId=0;
			if(bean.size()>0) {
				id=bean.get(0).getId();
				headerId=bean.get(0).getHeaderId();
			}
			
			for (QuestionnaireAnswerModelBean answer : bean) {
			    try {
			    	answer.setUpdatedBy(0L);
			        commonDao.insert("questionnaire.updateQuestionnaireAnswer", answer);
			    }catch(Exception ex) {
			    	log.error(ex.getMessage());
			    }
			}
			List<QuestionnaireAnswerModelBean> anslist=commonDao.selectList("questionnaire.getQuestionnaireAnswerDetail", Map.of("id",id,"headerId",headerId));
			return success(anslist);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}





	
	
	
}
