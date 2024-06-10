package th.co.locus.jlo.business.loyalty.product.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoyaltyProductRedemptionTransactionModelBean extends SortingModelBean {

	private Long txnId;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long programId;

	private Long memberTierId;
	private Long memberId;
	private Long cancelledTxnId;
	private Long refTxnId;

	private Integer productId;
	private Integer quantity;
	private Integer pointBefore;
	private Integer earnPoint;
	private Integer burnPoint;
	private Integer balancePoint;
	private BigDecimal requestPoint;
	private Integer progTierSpending;
	private Integer progTierPoint;
	private Integer cardTypeId;
	private Integer pointAdjustId;
	private Integer redemptionId;
	private Integer redeemPoint;
	private Integer refMemberId;

	private BigDecimal amount;
	private BigDecimal spending;
	private BigDecimal progTierLimitPerTime;
	private BigDecimal progTierLimitPerMonth;
	private BigDecimal progTierLimitPerDay;

	private String program;
	private String memberName;
	private String memberTierName;
	private String cardNumber;
	private String cardTier;
	private String productTypeId;
	private String productType;
	private String productName;
	private String loyProductTypeId;
	private String loyProductType;
	private String rewardTypeId;
	private String rewardType;
	private String channel;
	private String channelName;
	private String receiptId;

	private String storeShopId;
	private String storeShopType;
	private String txnTypeId;
	private String txnType;
	private String txnSubTypeId; // deprecate
	private String txnSubType;
	private String txnStatusId;
	private String txnStatus;
	private String subStatusId;
	private String subStatus;
	private String redeemMethod;
	private String redeemMethodName;

	private String processedDtl;
	private String referedCardNo;
	private String posTransId;
	private String posCancelTransId;
	private String pointTypeId;
	private String pointType;

	private String progOverrideBasePointYn;
	private String productCode;
	private BigDecimal requestCash;

	private String location;
	private String cardPrefix;
	private String posCancelTypeId;
	private String posCancelType;
	private String firstName;
	private String lastName;

	private Date receiptDate;
	private Date expiryDate;
	private Date posTransDate;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date processedDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	private Integer buId;
}
