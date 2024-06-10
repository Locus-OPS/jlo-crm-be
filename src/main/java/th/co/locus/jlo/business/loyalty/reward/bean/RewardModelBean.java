package th.co.locus.jlo.business.loyalty.reward.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.business.loyalty.product.bean.ProductModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class RewardModelBean extends ProductModelBean {
	private String rewardTypeId;
	private String rewardType;
	private Double rewardPrice;
	private Double rewardTax;
	private String rewardCategoryId;
	private String rewardCategory;
	private String rewardSubCategoryId;
	private String rewardSubCategory;
	private Integer rewardUnitPerson;
	private String rewardActiveFlag;
	private String rewardChannelWebPortalFlag;
	private String rewardChannelLineFlag;
	private String rewardChannelMobileFlag;
	private String rewardChannelKiosFlag;
}
