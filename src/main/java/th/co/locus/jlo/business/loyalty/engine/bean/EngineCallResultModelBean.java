package th.co.locus.jlo.business.loyalty.engine.bean;

import lombok.Data;

@Data
public class EngineCallResultModelBean {
	
	private String statusCode;
	private String errorCode;
	private String errorMessage;

}
