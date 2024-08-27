package th.co.locus.jlo.business.qustionnaire.bean;

import java.util.List;

//import java.io.Serializable;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireHeaderModelBean extends BaseModelBean 
//implements Serializable 
{

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private Long id;
	private String questionnaireType;
	private String formName;
	private String sectionHeaderText;
	private String statusCd;
	private String urlLink;
	private String hashKey;
	private List<QuestionnaireQuestionModelBean> questionnaireList;
}
