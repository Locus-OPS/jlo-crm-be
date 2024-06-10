package th.co.locus.jlo.business.loyalty.saleproductcategory.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@Alias("saleProductCategoryModelBean")
public class SaleProductCategoryModelBean extends BaseModelBean {
	
	private Long categoryCode;
	private String categoryName;
	private Long parentCategoryCode;
	private String level;
	private String categoryStatus;
	private String remark;
	private String categoryStatusName;
}
