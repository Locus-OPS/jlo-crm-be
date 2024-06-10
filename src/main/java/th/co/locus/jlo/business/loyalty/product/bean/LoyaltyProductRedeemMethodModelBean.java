package th.co.locus.jlo.business.loyalty.product.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoyaltyProductRedeemMethodModelBean extends SortingModelBean {

	private Long productRedeemMethodId;
	private Long productId;

	private String redeemMethodType;
	private String redeemMethodTypeName;

	private BigDecimal point;
	private BigDecimal cash;

	private Date createdDate;
	private String createdBy;

	private Date updatedDate;
	private String updatedBy;
}
