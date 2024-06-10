package th.co.locus.jlo.integration.line;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.integration.line.bean.LineMemberInfoModelBean;
import th.co.locus.jlo.integration.line.bean.LineMemberRegisterModelBean;
import th.co.locus.jlo.integration.line.bean.LineRedeemHistoryModelBean;

@Service
public class LineServiceImpl extends BaseService implements LineService {
	
	public ServiceResult<Long> registerLineToMember(LineMemberRegisterModelBean param) {		
		Long memberId = null;
		try {
			memberId = commonDao.selectOne("line.verifyMember", param);
			commonDao.insert("line.register", Map.of("memberId", memberId, "lineId", param.getLineId(), "type", param.getType()));
		} catch (Exception e) {
			fail("100", "Please contact administrator.");
		}
		return success(memberId);		
	}

	@Override
	public ServiceResult<Long> checkLineRegister(String lineId) {
		return success(commonDao.selectOne("line.checkLineRegister", Map.of("lineId", lineId)));
	}

	@Override
	public ServiceResult<LineMemberInfoModelBean> getMemberInfo(Long memberId) {
		return success(commonDao.selectOne("line.getMemberInfo", Map.of("memberId", memberId)));
	}

	@Override
	public ServiceResult<List<LineRedeemHistoryModelBean>> getRedeemHistory(Long memberId) {
		return success(commonDao.selectList("line.getRedeemHistory", Map.of("memberId", memberId)));
	}

}
