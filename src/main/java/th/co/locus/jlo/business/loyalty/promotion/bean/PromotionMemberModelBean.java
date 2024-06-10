package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PromotionMemberModelBean extends SortingModelBean {
	
	private Long promotionMemberId;
	private Long promotionId;
	private Long memberId;
	
	private String title;
	private String firstName;
	private String lastName;
	private String cardNo;
	private String memberTierId;
	private String tierName;
	
	private LocalDateTime createdDate;
	private String createdBy;
	
	private LocalDateTime updatedDate;
	private String updatedBy;
}
