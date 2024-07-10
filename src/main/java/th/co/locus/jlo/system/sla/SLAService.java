package th.co.locus.jlo.system.sla;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.sla.bean.SLACriteriaModelBean;
import th.co.locus.jlo.system.sla.bean.SLAModelBean;

public interface SLAService {

	public ServiceResult<Page<SLAModelBean>> getSLAList(SLACriteriaModelBean criteria, PageRequest pageRequest);

	public ServiceResult<SLAModelBean> createSLA(SLAModelBean bean);

	public ServiceResult<SLAModelBean> updateSLA(SLAModelBean bean);

	public ServiceResult<Integer> deleteSLA(SLAModelBean bean);

}
