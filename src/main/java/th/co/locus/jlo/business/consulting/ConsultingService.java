package th.co.locus.jlo.business.consulting;

import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface ConsultingService {

	public ServiceResult<ConsultingModelBean> insertConsultingInital(ConsultingModelBean inital);

	public ServiceResult<ConsultingModelBean> getConsultingData(ConsultingModelBean bean);

	public ServiceResult<ConsultingModelBean> updateConsulting(ConsultingModelBean bean);
	
	public ServiceResult<ConsultingModelBean> updateConsultingBindingCustomer(ConsultingModelBean bean);
	
	
	public ServiceResult<ConsultingModelBean> updateStopConsulting(ConsultingModelBean bean);

	public ServiceResult<Page<ConsultingModelBean>> getConsultingDataList(ConsultingModelBean req,
																		  PageRequest pageRequest);
	
	public ServiceResult<Page<CaseModelBean>> getCaseUnderConsultingList(ConsultingModelBean req,
			PageRequest pageRequest);
	
	public ServiceResult<Page<ConsultingModelBean>> getConsultingDataListByCustomerId(ConsultingModelBean req,
			PageRequest pageRequest);
	
	
}
