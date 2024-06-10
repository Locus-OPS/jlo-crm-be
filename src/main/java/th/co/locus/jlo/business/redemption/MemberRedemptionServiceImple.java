package th.co.locus.jlo.business.redemption;

import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.redemption.bean.RedemptionMemberInfoModelBean;
import th.co.locus.jlo.business.redemption.bean.RedemptionRewardModelBean;
import th.co.locus.jlo.business.redemption.bean.SearchRedemptionRewardModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class MemberRedemptionServiceImple extends BaseService implements MemberRedemptionService {

	@Override
	public ServiceResult<RedemptionMemberInfoModelBean> getMemberInfo(Long memberId) {
		return success(commonDao.selectOne("redemption.getMemberInfo", Map.of("memberId", memberId)));
	}

	@Override
	public ServiceResult<Page<RedemptionRewardModelBean>> searchReward(SearchRedemptionRewardModelBean param, PageRequest pageRequest) {
		return success(commonDao.selectPage("redemption.searchReward", param, pageRequest));
	}

}
