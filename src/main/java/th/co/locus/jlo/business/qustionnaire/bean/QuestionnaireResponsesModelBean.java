package th.co.locus.jlo.business.qustionnaire.bean;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireResponsesModelBean extends BaseModelBean {
	private Long responseId;
	private Long respondentId;
	private Long questionnaireHeaderId;
	private Long questionId;
	private String responseText;
}
