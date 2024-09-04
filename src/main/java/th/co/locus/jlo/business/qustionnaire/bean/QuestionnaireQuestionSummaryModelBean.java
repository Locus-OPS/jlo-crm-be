package th.co.locus.jlo.business.qustionnaire.bean;

import java.util.List;

import lombok.Data;

@Data
public class QuestionnaireQuestionSummaryModelBean {
	private List<QuestionnaireQuestionModelBean> question;
//	private List<QuestionnaireDashboardValueModelBean> summaryDetail;
}
