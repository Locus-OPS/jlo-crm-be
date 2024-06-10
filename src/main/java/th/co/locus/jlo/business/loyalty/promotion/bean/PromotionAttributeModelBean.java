package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PromotionAttributeModelBean extends SortingModelBean {
	
	private Long promotionAttrId;
	private Long programId;
	private Long promotionId;
	
	private String promotionAttr;
	private String promotionAttrDataTypeId;
	private String promotionAttrInitValue;
	private String activeFlag;
	
	private Long refLoyAttrId;
	
	private String memberId;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
	
	private Integer buId;
}
