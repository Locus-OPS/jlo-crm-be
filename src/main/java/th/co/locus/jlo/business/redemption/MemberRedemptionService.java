package th.co.locus.jlo.business.redemption;

import th.co.locus.jlo.business.redemption.bean.RedemptionMemberInfoModelBean;
import th.co.locus.jlo.business.redemption.bean.RedemptionRewardModelBean;
import th.co.locus.jlo.business.redemption.bean.SearchRedemptionRewardModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface MemberRedemptionService {
	
	public ServiceResult<RedemptionMemberInfoModelBean> getMemberInfo(Long memberId);
	public ServiceResult<Page<RedemptionRewardModelBean>> searchReward(SearchRedemptionRewardModelBean param, PageRequest pageRequest);

}
