package th.co.locus.jlo.business.loyalty.saleproduct.bean;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

/**
 * 
 * @author Mr.BoonOom
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SaleProductModelBean extends BaseModelBean {

	private Integer itemCode;
	private String itemName;
	private String itemStatus;
	private String statusName;
	private BigDecimal pricing;

	private Integer categoryCode;
	private String categoryName;
	
	private Integer unit;
	private String remark;

	private String langCd;
}
