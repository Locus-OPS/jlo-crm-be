package th.co.locus.jlo.external.api;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ApiQueryWorkflowRequest { 
	private String systemId; 
	private BigDecimal amount;
	
	
}
