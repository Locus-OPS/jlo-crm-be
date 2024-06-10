package th.co.locus.jlo.integration.line;

import java.util.List;

import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.integration.line.bean.LineMemberInfoModelBean;
import th.co.locus.jlo.integration.line.bean.LineMemberRegisterModelBean;
import th.co.locus.jlo.integration.line.bean.LineRedeemHistoryModelBean;

public interface LineService {

	public ServiceResult<Long> registerLineToMember(LineMemberRegisterModelBean param);
	public ServiceResult<Long> checkLineRegister(String lineId);
	public ServiceResult<LineMemberInfoModelBean> getMemberInfo(Long memberId);
	public ServiceResult<List<LineRedeemHistoryModelBean>> getRedeemHistory(Long memberId);
	
}
