package th.co.locus.jlo.business.loyalty.engine.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionEnrollModelBean {

	private Long memberId;       //required
	private String productCode;
	private String channel;
	private String receiptId;
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate receiptDate;
	private String spending; 
	
	
}
