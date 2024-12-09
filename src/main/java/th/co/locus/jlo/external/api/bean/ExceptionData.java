package th.co.locus.jlo.external.api.bean;

import lombok.Data;

@Data
public class ExceptionData {
	public ExceptionData() {
		this(null, null);
	}

	public ExceptionData(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;
}
