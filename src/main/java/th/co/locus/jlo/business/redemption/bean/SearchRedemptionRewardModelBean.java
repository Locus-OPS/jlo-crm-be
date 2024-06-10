package th.co.locus.jlo.business.redemption.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRedemptionRewardModelBean extends SortingModelBean {

	private Long memberId;
	private Long programId;
	private String displayType;
	private String rewardName;
	private String redeemMethod;
	private Integer minPoint;
	private Integer maxPoint;
	private Integer buId;
}
