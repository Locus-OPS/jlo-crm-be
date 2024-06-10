package th.co.locus.jlo.business.loyalty.shop.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchShopCriteriaModelBean extends SortingModelBean {

	private String shopName; 
	private Long   shopTypeId; 
	private String shopType;
	private String locationId; 
	private String activeFlag; 
	private Integer buId;
	private Long promotionId;
	private Long programId;
	
}
