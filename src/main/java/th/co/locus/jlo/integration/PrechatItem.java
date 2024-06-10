package th.co.locus.jlo.integration;

import lombok.Data;

@Data
public class PrechatItem {

	private String question;
	private String label;
	private boolean displayToAgent;
	private String transcriptField;
	private String entityName;
	private String fieldName;
	private String saveToTranscript;
	
}
