package th.co.locus.jlo.business.qustionnaire.bean;

import java.util.List;

import lombok.Data;

@Data
public class QuesionnaireRepondentResponseModelBean {
	private QuestionnaireRepondentsModelBean respodent;
	private List<QuestionnaireResponsesModelBean> responses;
}
