package th.co.locus.jlo.business.redemption.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RedemptionRewardModelBean {

	private Long productId;
	private String productCode;
	private String productName;
	private String productDetail;
	private Integer rewardUnitPerson;
	private BigDecimal point;
	private BigDecimal cash;
	private Long redeemCount;
	private Integer inventoryBalance;
	private String redeemMethodType;
	private String productImgPath;
	
	// from UI
	private Integer usePoint;
	private Integer useCash;
	private Integer quantity;
	
}
