package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchProductCriteriaModelBean extends SortingModelBean {
	
	private Long itemId;
    private String Item;
    
    private String parentCategory;
    private Long parentCategoryId;
     
    private String category;
    private Long categoryId;
    
    private Integer level;
	
	
}
