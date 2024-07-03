package th.co.locus.jlo.system.position;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.position.bean.PositionCriteriaModelBean;
import th.co.locus.jlo.system.position.bean.PositionModelBean;

@Service
public class PositionServiceImpl extends BaseService implements PositionService {

	@Override
	public ServiceResult<Page<PositionModelBean>> getPositionList(PositionCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("position.getPositionList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<PositionModelBean> createPosition(PositionModelBean bean) {
		int result = commonDao.update("position.createPosition", bean);
		if (result > 0) {
			return success(commonDao.selectOne("position.findPositionById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<PositionModelBean> updatePosition(PositionModelBean bean) {
		int result = commonDao.update("position.updatePosition", bean);
		if (result > 0) {
			return success(commonDao.selectOne("position.findPositionById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deletePosition(PositionModelBean bean) {
		return success(commonDao.delete("position.deletePosition", bean));
	}

}
