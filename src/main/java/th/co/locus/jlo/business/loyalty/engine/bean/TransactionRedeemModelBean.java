package th.co.locus.jlo.business.loyalty.engine.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionRedeemModelBean {


	 private Long memberId;     	 //required
	 private String productCode; 	 //required (as a reward)
	 private Integer requestCash;	 //required due to redeem method
	 private Integer requestPoint;	 //required due to redeem method
	 private Integer quantity;	 	 //required
	 private String redeemMethod;	 //required
	 private String channel;
	 private String shopId;
	 private String spending;
	 private String receiptId;
	 private String receiptNo;
	 
	 @JsonFormat(pattern="dd/MM/yyyy")
	 private LocalDate receiptDate;
	 
	 private String txnType;
	 private String txnSubType;
	 private String status;
	 private String productId;
	 private String productType;
	 
	 

	 
	 
}
