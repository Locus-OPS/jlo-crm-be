package th.co.locus.jlo.business.consulting;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

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
				return success(commonDao.selectOne("consulting.findConsultingData", inital));
			}
		}

		return fail();

	}

	@Override
	public ServiceResult<ConsultingModelBean> insertConsultingEmailInbound(ConsultingModelBean inital) {

		String consultingNumber = commonDao.selectOne("consulting.generateConsultingNumber", inital);
		if (consultingNumber != null && consultingNumber != "") {
			inital.setConsultingNumber(consultingNumber);
			int resultInsert = commonDao.insert("consulting.createConsultingEmailInbound", inital);
			if (resultInsert > 0) {
				return success(commonDao.selectOne("consulting.findConsultingData", inital));
			}
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
			return success(commonDao.selectOne("consulting.findConsultingData", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<ConsultingModelBean>> getConsultingDataList(ConsultingModelBean req,
																		  PageRequest pageRequest) {
		return success(commonDao.selectPage("consulting.findConsultingDataList", req, pageRequest));
	}



	@Override
	public ServiceResult<ConsultingModelBean> updateConsultingBindingCustomer(ConsultingModelBean bean) {
		int result = commonDao.update("consulting.updateConsultingBindingCustomer", bean);
		if (result > 0) {
			return success(commonDao.selectOne("consulting.findConsultingById", bean));
		}
		return fail();
	}



	@Override
	public ServiceResult<Page<CaseModelBean>> getCaseUnderConsultingList(ConsultingModelBean req,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("consulting.getCaseUnderConsultingList", req, pageRequest));
	}



	@Override
	public ServiceResult<ConsultingModelBean> updateStopConsulting(ConsultingModelBean bean) {
		int result = commonDao.update("consulting.updateStopConsulting", bean);
		if (result > 0) {
			return success(commonDao.selectOne("consulting.findConsultingById", bean));
		}
		return fail();
	}



	@Override
	public ServiceResult<Page<ConsultingModelBean>> getConsultingDataListByCustomerId(ConsultingModelBean req,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("consulting.findConsultingDataListByCustomerId", req, pageRequest));
	}



	@Override
	public ServiceResult<List<ConsultingModelBean>> getConsultingTimelineDataListByCustomerId(
			ConsultingModelBean bean) {
		return success(commonDao.selectList("consulting.findConsultingDataListByCustomerId", bean)); 
	}



	 
	
}
