package th.co.locus.jlo.integration.sfdc.bean;

import lombok.Data;

@Data
public class PrechatDetail {

	private String label;
	private String value;
	private String[] transcriptFields;
	private boolean displayToAgent;
	
}
