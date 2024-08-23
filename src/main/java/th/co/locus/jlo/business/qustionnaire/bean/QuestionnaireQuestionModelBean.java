package th.co.locus.jlo.business.qustionnaire.bean;

import java.io.Serializable;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireQuestionModelBean extends BaseModelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long headerId;
	private String question;
	private String answerType;
	private String description;
	private String imageUrl;
	private String statusCd;
	private Boolean requiredFlg;
	private Long seqNo;

}
