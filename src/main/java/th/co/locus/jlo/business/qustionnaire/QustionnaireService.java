package th.co.locus.jlo.business.qustionnaire;

import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface QustionnaireService {
	public ServiceResult<QuestionnaireHeaderModelBean> getQuestionnaireById(QuestionnaireHeaderModelBean bean);
	public ServiceResult<QuestionnaireHeaderModelBean> createHeaderQuestionnaire(QuestionnaireHeaderModelBean bean);
	public ServiceResult<QuestionnaireHeaderModelBean> updateHeaderQuestionnaire(QuestionnaireHeaderModelBean bean);
}
