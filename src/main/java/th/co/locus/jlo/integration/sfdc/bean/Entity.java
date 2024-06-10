package th.co.locus.jlo.integration.sfdc.bean;

import lombok.Data;

@Data
public class Entity {

	private String entityName;
	private boolean showOnCreate;
	private String linkToEntityName;
	private String linkToEntityField;
	private String saveToTranscript;
	private EntityFieldsMap[] entityFieldsMaps;
	
}
