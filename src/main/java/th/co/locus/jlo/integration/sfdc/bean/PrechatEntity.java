package th.co.locus.jlo.integration.sfdc.bean;

import lombok.Data;

@Data
public class PrechatEntity {

	private String entityName;
	private String saveToTranscript;
	private String linkToEntityName;
	private String linkToEntityField;
	private EntityFieldsMap[] entityFieldsMaps;
	
}
