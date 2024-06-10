package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@Alias("PromotionProductModelBean")
public class ProductModelBean extends SortingModelBean {

	private Long promotionProductId;
	private Long promotionId;
	private Long productCode;
	
	private String product;
	private String productCategory;
	private String productParentCategory;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
}
