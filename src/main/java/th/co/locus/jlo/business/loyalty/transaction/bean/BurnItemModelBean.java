package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class BurnItemModelBean extends BaseModelBean {

    private Long txnId;
	private Long earnId;
	private Long burnId;
	
	private Integer value;
	
	private Integer promotionId;
	private String promotion;   
	
	private Integer ruleId;
	private String rule;
	
	private Integer actionId;   
	private String actionDetail;   
	
	
}
