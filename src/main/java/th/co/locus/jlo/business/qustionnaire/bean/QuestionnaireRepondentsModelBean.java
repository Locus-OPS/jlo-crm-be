package th.co.locus.jlo.business.qustionnaire.bean;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireRepondentsModelBean extends BaseModelBean {
	private Long respondentId;
	private Long questionnaireHeaderId;
	private String name;
	private int age;
	private String gender;
	private String location;
}
