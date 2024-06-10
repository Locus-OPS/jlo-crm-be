package th.co.locus.jlo.business.loyalty.receipt.bean;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReceiptModelBean {
	
	private String memberId;
	private String memberName;
	private String memberFirstName;
	private String memberLastName;
	
	private String programId;
	private String memberTierId;
	private String memberTierName;
	private String receiptId;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate receiptDate;
	
	private String shopId;
	private String shopType;
	private String shopName;
	private String spending;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String createdName;
	private String updatedBy;
	private String updatedName;
	private Integer buId;
	
	
	
}
