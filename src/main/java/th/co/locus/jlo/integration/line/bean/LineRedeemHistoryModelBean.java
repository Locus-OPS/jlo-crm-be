package th.co.locus.jlo.integration.line.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LineRedeemHistoryModelBean {

	private Long txnId;
	private String productType;
	private String productName;
	private Integer pointBefore;
	private Integer burnPoint;
	private Integer balancePoint;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date processedDate;
	
}
