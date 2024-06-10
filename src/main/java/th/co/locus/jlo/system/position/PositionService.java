package th.co.locus.jlo.system.position;

import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.system.position.bean.PositionCriteriaModelBean;
import th.co.locus.jlo.system.position.bean.PositionModelBean;

public interface PositionService {

	public ServiceResult<Page<PositionModelBean>> getPositionList(PositionCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PositionModelBean> createPosition(PositionModelBean bean);
	public ServiceResult<PositionModelBean> updatePosition(PositionModelBean bean);
	public ServiceResult<Integer> deletePosition(PositionModelBean bean);
	
}
