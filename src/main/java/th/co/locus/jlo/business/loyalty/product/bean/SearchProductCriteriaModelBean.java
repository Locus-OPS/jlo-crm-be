package th.co.locus.jlo.business.loyalty.product.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("searchLoyaltyProductModelBean")
public class SearchProductCriteriaModelBean extends SortingModelBean {

	private String rewardCode;
	private String reward;

	private String rewardTypeId;
	private Long campaignId;

	private String productType;
	private Long productId;

	private String productCode;
	private String preciseProductCode;
	private String product;

	private String rewardType;
	private Long programId;

	private String productActiveFlag;
	private String productCategoryId;
	private String productTypeId;
	private String loyProductTypeId;
	private Integer buId;
}
