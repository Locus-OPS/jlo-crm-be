package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class EarnItemModelBean extends BaseModelBean {

    private Long txnId;
	private Long earnId;
	
	private Integer promotionId;
	private String promotion;   
	
	private Integer ruleId;
	private String rule;
	
	private Integer actionId;   
	private String actionDetail;   
	
	private Integer earnValue;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date expiryDate; 
	
	private Integer pointTypeId;
	private String pointType;
	
	
}
