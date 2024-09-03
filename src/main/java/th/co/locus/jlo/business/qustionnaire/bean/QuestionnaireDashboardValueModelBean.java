package th.co.locus.jlo.business.qustionnaire.bean;

import lombok.Data;

@Data
public class QuestionnaireDashboardValueModelBean {
	private String name;
	private String value;
	public QuestionnaireDashboardValueModelBean(String name,String value){
		this.name=name;
		this.value=value;
	}
}
