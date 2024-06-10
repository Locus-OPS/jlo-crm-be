package th.co.locus.jlo.business.loyalty.modal.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ShopModalBean extends BaseModelBean {
	
	private String shopId;
	private String shopName;
	private String shopTypeId;
	private String shopTypeName;
	private String shopLocation;
	private String shopLocationName;
	private String shopFloor;
	private String shopNo;
	
}
