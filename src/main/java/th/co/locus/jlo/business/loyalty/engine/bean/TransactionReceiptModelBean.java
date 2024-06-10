package th.co.locus.jlo.business.loyalty.engine.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionReceiptModelBean {

	
	private String memberId;		//required
    private String receiptNo;		//required
	   
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate receiptDate;     //required
	private String spending;        //required
	private String shopId;          //required
	private String channel;			//required ( system that send this receipt )
	
	private String receiptId;      //unique key for system that send this receipt ( but loyalty not required )
	
	private String txnId;
	private String productCode;    //parameter for specific product code ( if need ) 
	
	
}
