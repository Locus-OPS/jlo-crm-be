package th.co.locus.jlo.business.qustionnaire.bean;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireAnswerModelBean extends BaseModelBean implements Serializable {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long questionId;
	private String answer;
	private String statusCd;
}
