package th.co.locus.jlo.business.consulting;

import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface ConsultingService {

	public ServiceResult<ConsultingModelBean> insertConsultingInital(ConsultingModelBean inital);

	public ServiceResult<ConsultingModelBean> getConsultingData(ConsultingModelBean bean);

	public ServiceResult<ConsultingModelBean> updateConsulting(ConsultingModelBean bean);
	
	public ServiceResult<ConsultingModelBean> updateConsultingBindingCustomer(ConsultingModelBean bean);
	
	

	public ServiceResult<Page<ConsultingModelBean>> getConsultingDataList(ConsultingModelBean req,
			PageRequest pageRequest);

}
