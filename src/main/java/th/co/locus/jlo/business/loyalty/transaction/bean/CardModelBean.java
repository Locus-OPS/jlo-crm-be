package th.co.locus.jlo.business.loyalty.transaction.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardModelBean extends BaseModelBean {

  	private Long programId;
    private String firstName;
    private String lastName;
    private String fullNameEn;
	private String cardNumber;
	private String cardPrefix;
	private Long memberId; 
	
	
}
