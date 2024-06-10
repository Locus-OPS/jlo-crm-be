package th.co.locus.jlo.business.loyalty.transaction.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductModelBean extends BaseModelBean {

  	private Long itemId;
    private String Item;
    
    private String parentCategory;
    private Long parentCategoryId;
     
    private String category;
    private Long categoryId;
    
    private Integer level;
    
	
}
