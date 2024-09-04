package th.co.locus.jlo.business.qustionnaire;

import java.util.List;

import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardValueModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionSummaryModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface QuestionnaireDashboardService {
	ServiceResult<QuestionnaireMainDashboardModelBean> getMainDashboad(Long headerId);
	ServiceResult<Page<QuestionnaireRepondentsModelBean>> getRespondent(QuestionnaireRepondentsModelBean bean,PageRequest page);
	
	ServiceResult<QuestionnaireQuestionSummaryModelBean> getQuestionResponseSummary(Long headerId);
	ServiceResult<QuestionnaireQuestionSummaryModelBean> getQuestionResponseDetail(Long headerId,Long respondentId);
	ServiceResult<List<QuestionnaireDashboardValueModelBean>> getQuestionnaireSummaryText(QuestionnaireQuestionModelBean bean);
	
	void exportQuestionnaireSummary(QuestionnaireQuestionModelBean bean);
	void exportQuestionnaireSummaryList(QuestionnaireHeaderModelBean bean);
}
