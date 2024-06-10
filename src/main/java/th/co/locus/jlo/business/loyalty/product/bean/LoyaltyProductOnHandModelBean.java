package th.co.locus.jlo.business.loyalty.product.bean;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoyaltyProductOnHandModelBean {
	
	private Long productOnhandId;
	private Long productId;
	private String onhandType;
	private Integer amount;
	private String remark;
	
	private Date createdDate;
	private String createdBy;
	
	private Date updatedDate;
	private String updatedBy;
}
