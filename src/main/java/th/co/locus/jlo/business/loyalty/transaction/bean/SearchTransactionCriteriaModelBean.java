package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchTransactionCriteriaModelBean extends SortingModelBean {

    private Long programId;
	private String memberName;
	private String txnTypeId;
	private String txnStatusId;
	private String cardNumber;
	private Long memberId;
	private Long cancelledTxnId;
	private Long txnId;
	private String receiptId;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date receiptDate;
	private Integer buId;
}
