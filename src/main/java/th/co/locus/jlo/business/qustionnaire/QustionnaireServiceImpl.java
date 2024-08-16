package th.co.locus.jlo.business.qustionnaire;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Service
public class QustionnaireServiceImpl extends BaseService implements QustionnaireService {

	@Override
	public ServiceResult<QuestionnaireHeaderModelBean> getQuestionnaireById(QuestionnaireHeaderModelBean bean) {
		return success(commonDao.selectOne("qustionnaire.getQuestionnaireById", bean));
	}

	
	
	
}
