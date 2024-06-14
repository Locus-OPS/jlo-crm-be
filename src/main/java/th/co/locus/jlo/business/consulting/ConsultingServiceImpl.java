package th.co.locus.jlo.business.consulting;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Slf4j
@Service
public class ConsultingServiceImpl extends BaseService implements ConsultingService {
	@Override
	public ServiceResult<ConsultingModelBean> insertConsultingInital(ConsultingModelBean inital) {

		String consultingNumber = commonDao.selectOne("consulting.generateConsultingNumber", inital);
		if (consultingNumber != null && consultingNumber != "") {
			inital.setConsultingNumber(consultingNumber);
			int resultInsert = commonDao.insert("consulting.createConsulting", inital);
			if (resultInsert > 0) {
				return success(commonDao.selectOne("consulting.findConsultingById", inital));
			}
		}

		return fail();

	}

	@Override
	public ServiceResult<ConsultingModelBean> stopConsulting(ConsultingModelBean consultBean) throws Exception {
		int result = commonDao.update("consulting.updateConsulting", consultBean);
		if (result > 0) {
			return success(commonDao.selectOne("consulting.findConsultingById", consultBean));
		}
		return fail();
		 
	}

	@Override
	public ServiceResult<ConsultingModelBean> getConsultingData(ConsultingModelBean bean) {
		return success(commonDao.selectOne("consulting.findConsultingData", bean));
	}

	@Override
	public ServiceResult<ConsultingModelBean> updateConsulting(ConsultingModelBean bean) {
		int result = commonDao.update("consulting.updateConsulting", bean);
		if (result > 0) {
			return success(commonDao.selectOne("consulting.findConsultingById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<ConsultingModelBean>> getConsultingDataList(ConsultingModelBean req,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("consulting.findConsultingDataList", req, pageRequest));
	}

}
