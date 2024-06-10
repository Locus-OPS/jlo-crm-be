package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PromotionAttrModelBean extends BaseModelBean {

	private Long promotionId;
	private String promotion;   

	private Long memberId;
	
	private String attrName;
	private String attrType;
	private String attrValue;
	
	private Date processedDate;
	
}
