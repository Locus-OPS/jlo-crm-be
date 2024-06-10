package th.co.locus.jlo.integration.sfdc.bean;

import lombok.Data;

@Data
public class EntityFieldsMap {

	private String fieldName;
	private String label;
	private boolean doFind;
	private boolean isExactMatch;
	private boolean doCreate;
	
}
