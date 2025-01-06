package th.co.locus.jlo.external.api.req.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ApiWorkflowRequestBean { 
	private String systemId; 
	private BigDecimal amount;
	private String financeFlg;
}
