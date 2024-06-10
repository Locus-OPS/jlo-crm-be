package th.co.locus.jlo.business.loyalty.promotion.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductModalModelBean extends SortingModelBean {

  	private Long itemId;
    private String Item;
    
    private String parentCategory;
    private Long parentCategoryId;
     
    private String category;
    private Long categoryId;
    
    private Integer level;
    
    private Integer buId;
    private Long promotionId;
	
}
