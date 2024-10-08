package th.co.locus.jlo.business.qustionnaire.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class QuestionnaireQuestionModelBean extends BaseModelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long headerId;
	private String question;
	private String answerType;
	private String answerTypeName;
	private String options;
	private String description;
	private String imageUrl;
	private String statusCd;
	private Boolean requiredFlg;
	private Long seqNo;
	private String componentType;
	
	//เอาไว้สร้าง Dashboard เช่น กราฟต่างๆ
	private List<QuestionnaireDashboardValueModelBean> summaryDetail;
	//เอาไว้แสดงคำตอบ
	private List<QuestionnaireDashboardValueModelBean> responseDetail;

}
