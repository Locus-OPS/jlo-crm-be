package th.co.locus.jlo.business.loyalty.saleproductcategory.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("searchSaleProductCategoryCriteriaModelBean")
public class SearchSaleProductCategoryCriteriaModelBean  extends SortingModelBean {
	
	private Long categoryCode;
	private String categoryName;
	private String parentCategoryCode;
	private Integer level;
	private String categoryStatus;
	private String remark;
	private Integer buId;
}
