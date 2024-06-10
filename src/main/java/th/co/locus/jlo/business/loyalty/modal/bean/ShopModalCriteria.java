package th.co.locus.jlo.business.loyalty.modal.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ShopModalCriteria extends SortingModelBean {
	
	private String shopNo;
	private String shopName;
	private String shopTypeId;
	private String shopLocation;
	private String shopFloor;
	private Integer buId;
}
