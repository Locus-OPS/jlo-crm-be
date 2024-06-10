package th.co.locus.jlo.business.redemption.bean;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubmitRedeemModelBean {

	private Long memberId;
	private List<RedemptionRewardModelBean> rewardList;
	
}
