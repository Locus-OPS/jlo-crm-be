package th.co.locus.jlo.system.sla;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.sla.bean.SLACriteriaModelBean;
import th.co.locus.jlo.system.sla.bean.SLAModelBean;

@Service
public class SLAServiceImpl extends BaseService implements SLAService {

	@Override
	public ServiceResult<Page<SLAModelBean>> getSLAList(SLACriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("sla.getSLAList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<SLAModelBean> createSLA(SLAModelBean bean) {
		int result = commonDao.update("sla.createSLA", bean);
		if (result > 0) {
			return success(commonDao.selectOne("sla.findSLAById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<SLAModelBean> updateSLA(SLAModelBean bean) {
		int result = commonDao.update("sla.updateSLA", bean);
		if (result > 0) {
			return success(commonDao.selectOne("sla.findSLAById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteSLA(SLAModelBean bean) {
		return success(commonDao.delete("SLA.deleteSLA", bean));
	}

}
