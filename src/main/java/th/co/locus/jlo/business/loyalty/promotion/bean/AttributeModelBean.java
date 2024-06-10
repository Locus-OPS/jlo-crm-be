package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class AttributeModelBean extends SortingModelBean {
	
	private Long promotionAttrId;
	private Long programId;
	private Long promotionId;
	
	private String promotionAttr;
	private String promotionAttrDataType;
	private String promotionAttrInitValue;
	
	private String activeFlag;
	
	private Long refLoyAttrId;
	private Long memberId;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
}
