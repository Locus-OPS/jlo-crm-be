package th.co.locus.jlo.business.loyalty.shop.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShopTypeModelBean extends BaseModelBean {

	private Long shopTypeId;
	private String shopTypeName;
	private String activeFlag;
	
}
