package th.co.locus.jlo.business.qustionnaire;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
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
		return success(commonDao.selectOne("qustionnaire.getQuestionnaireById", bean));
	}

	@Override
	public ServiceResult<QuestionnaireHeaderModelBean> createHeaderQuestionnaire(QuestionnaireHeaderModelBean bean) {
	
		int result = commonDao.update("questionnaire.createHeaderQuestionnaire", bean);
		log.info("result : "+result);
		log.info("Service : "+bean.toString());
		if (result > 0) {
			return success(commonDao.selectOne("questionnaire.findQuestionnaireById", bean));
		}
		return fail(); 
	}

	@Override
	public ServiceResult<QuestionnaireHeaderModelBean> updateHeaderQuestionnaire(QuestionnaireHeaderModelBean bean) {
		try {
			log.info("Service : "+bean.toString());
			int result = commonDao.update("questionnaire.updateHeaderQuestionnaire", bean);
			log.info("Result : "+result);
			if (result > 0) {
				return success(bean);
			}
			return fail();
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}


	}



	
	
	
}
