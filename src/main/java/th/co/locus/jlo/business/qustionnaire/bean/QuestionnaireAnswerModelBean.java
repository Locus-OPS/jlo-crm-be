package th.co.locus.jlo.business.qustionnaire.bean;

import java.io.Serializable;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireAnswerModelBean extends BaseModelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String questionId;
	private String answer;
	private String requiredFlg;
	private String statusCd;

}
