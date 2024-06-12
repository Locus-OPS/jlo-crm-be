package th.co.locus.jlo.business.consulting;

import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.common.ServiceResult;

public interface ConsultingService {
	
	public ServiceResult<ConsultingModelBean> insertConsultingInital(ConsultingModelBean inital);
	public ServiceResult<ConsultingModelBean> stopConsulting(ConsultingModelBean consultBean) throws Exception;

	

}
