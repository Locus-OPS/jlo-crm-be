package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
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
			return success(commonDao.selectOne("questionnaire.findQuestionnaireById", bean));
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





	
	
	
}
