package th.co.locus.jlo.business.qustionnaire.bean;

import java.util.List;

import lombok.Data;

@Data
public class QuestionnaireMainDashboardModelBean {
	private Long headerId;
	private Long totalRespondent;
	private List<QuestionnaireDashboardValueModelBean> genderGroup;
	private List<QuestionnaireDashboardValueModelBean> ageGroup;
}
