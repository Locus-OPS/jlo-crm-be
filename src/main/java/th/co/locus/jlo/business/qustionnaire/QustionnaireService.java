package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import th.co.locus.jlo.business.qustionnaire.bean.QuesionnaireRepondentResponseModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireResponsesModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface QustionnaireService {
	public ServiceResult<Page<QuestionnaireHeaderModelBean>> getHeaderQuestionaire(QuestionnaireHeaderModelBean bean,PageRequest page);
	public ServiceResult<QuestionnaireHeaderModelBean> getQuestionnaireById(QuestionnaireHeaderModelBean bean);
	public ServiceResult<QuestionnaireHeaderModelBean> createHeaderQuestionnaire(QuestionnaireHeaderModelBean bean);
	public ServiceResult<QuestionnaireHeaderModelBean> updateHeaderQuestionnaire(QuestionnaireHeaderModelBean bean);
	
	public ServiceResult<List<QuestionnaireQuestionModelBean>> getQuestionnaireQuestionList(QuestionnaireQuestionModelBean bean);
	public ServiceResult<QuestionnaireQuestionModelBean> getQuestionnaireQuestionDetail(QuestionnaireQuestionModelBean bean);
	public ServiceResult<QuestionnaireQuestionModelBean> createQuestionnaireQuestion(QuestionnaireQuestionModelBean bean);
	public ServiceResult<QuestionnaireQuestionModelBean> updateQuestionnaireQuestion(QuestionnaireQuestionModelBean bean);
	
	public ServiceResult<QuesionnaireRepondentResponseModelBean> createQuestionnaireResponse(QuesionnaireRepondentResponseModelBean bean);
	public ServiceResult<QuesionnaireRepondentResponseModelBean> getQuestionnaireResponse(QuesionnaireRepondentResponseModelBean bean);
	public ServiceResult<Page<QuestionnaireRepondentsModelBean>> getQuestionnaireRepondentsList(QuestionnaireHeaderModelBean bean,PageRequest page);
	public ServiceResult<Page<QuestionnaireResponsesModelBean>> getQuestionnaireResponseList(QuestionnaireRepondentsModelBean bean,PageRequest page);
//	public ServiceResult<Page<QuestionnaireAnswerModelBean>> getQuestionnaireAnswerList(QuestionnaireAnswerModelBean bean,PageRequest page);
//	public ServiceResult<Page<QuestionnaireAnswerModelBean>> getQuestionnaireAnswerResult(QuestionnaireAnswerModelBean bean,PageRequest page);
//	public ServiceResult<QuestionnaireAnswerModelBean> createQuestionnaireAnswer(QuestionnaireAnswerModelBean bean);
//	public ServiceResult<List<QuestionnaireAnswerModelBean>> updateQuestionnaireAnswer(List<QuestionnaireAnswerModelBean> bean);
	
}
