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
	private Long headerId;
	private Long questionId;
	private String answer;
//	private String requiredFlg;
	private String statusCd;
	private Long seqNo;
	private String qAndA;
	

}
