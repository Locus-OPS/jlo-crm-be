package th.co.locus.jlo.business.sr;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.sr.bean.SearchSrModelBean;
import th.co.locus.jlo.business.sr.bean.ServiceRequestModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Slf4j
@Service
public class ServiceRequestServiceImpl extends BaseService implements ServiceRequestService{

	@Override
	public ServiceResult<Page<ServiceRequestModelBean>> getSrList(SearchSrModelBean bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("sr.getSrList", bean, pageRequest));
	}
	
	@Override
	public ServiceResult<ServiceRequestModelBean> getSrBySrNumber(String srNumber) {
		return success(commonDao.selectOne("sr.getSrBySrNumber", srNumber));
	}
	
	@Override
	public ServiceResult<ServiceRequestModelBean> createSr(ServiceRequestModelBean bean) {
		
		String srNumber = commonDao.selectOne("sr.generateSrNumber", bean);
		if (srNumber != null && srNumber != "") {
			bean.setSrNumber(srNumber);
			//int result = commonDao.update("sr.createCase", bean); 
			String result = commonDao.selectOne("sr.createSrProcedure", bean);
			log.info("result > "+result);
			if(result != null) {
				return success(commonDao.selectOne("sr.getSrBySrNumber", srNumber));
			}
		}
		return fail();
	}
	
	@Override
	public ServiceResult<ServiceRequestModelBean> updateSr(ServiceRequestModelBean bean) {
		
		int result = commonDao.update("sr.updateSrProc", bean);
		if (result > 0) {
			return success(commonDao.selectOne("sr.getSrBySrNumber", bean));
		}
		return fail();
	}
}
