package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class TransactionModelBean extends BaseModelBean {

	private Long txnId;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long programId;

	private Long memberTierId;
	private Long memberId;
	private Long cancelledTxnId;
	private Long refTxnId;

	private Integer productId;
	private Integer cardTierId;
	private Integer quantity;
	private Integer pointBefore;
	private Integer earnPoint;
	private Integer burnPoint;
	private Integer balancePoint;
	private Integer requestPoint;
	private Integer progTierSpending;
	private Integer progTierPoint;
	private Integer cardTypeId;
	private Integer pointAdjustId;
	private Integer redemptionId;
	private Integer redeemPoint;
	private Integer refMemberId;
	private Integer buId;

	private BigDecimal amount;
	private BigDecimal spending;
	private BigDecimal progTierLimitPerTime;
	private BigDecimal progTierLimitPerMonth;
	private BigDecimal progTierLimitPerDay;

	private String program;
	private String memberName;
	private String memberMobile;
	private String memberTier;
	private String cardNumber;
	private String cardTier;
	private String productTypeId;
	private String productType;
	private String productName;
	private String product;
	private String loyProductTypeId;
	private String loyProductType;
	private String rewardTypeId;
	private String rewardType;
	private String channel;
	private String receiptId;
	private String receiptNo;

	private String storeShopId;
	private String storeShopName;
	private String storeShopTypeId;
	private String storeShopType;
	private String storeShopTypeName;

	private String txnTypeId;
	private String txnType;
	private String txnSubTypeId; // deparecate
	private String txnSubType;
	private String txnStatusId;
	private String txnStatus;
	private String subStatusId;
	private String subStatus;
	private String redeemMethodId;
	private String redeemMethod;

	private String processedDtl;
	private String referedCardNo;
	private String posTransId;
	private String posCancelTransId;
	private String pointTypeId;

	private String progOverrideBasePointYn;
	private String productCode;
	private Integer requestCash;

	private String location;
	private String cardPrefix;
	private String posCancelTypeId;
	private String posCancelType;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate receiptDate;
	private Date expiryDate;
	private Date posTransDate;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date processedDate;
	@JsonFormat(pattern = "HH:mm:ss")
	private Date processedTime;
}
