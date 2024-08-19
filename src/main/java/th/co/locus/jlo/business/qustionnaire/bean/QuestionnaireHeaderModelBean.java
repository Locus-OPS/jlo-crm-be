package th.co.locus.jlo.business.qustionnaire.bean;

import java.io.Serializable;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireHeaderModelBean extends BaseModelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String questionnaireType;
	private String formName;
	private String sectionHeaderText;
	private String statusCd;

}
