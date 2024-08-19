package th.co.locus.jlo.business.qustionnaire.bean;

import lombok.Data;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
public class SearchQuestionnaireModelBean extends SortingModelBean{
	private Long id;
	private String questionnaireType;
	private String formName;
	private String sectionHeaderText;
	private String statusCd;
}
