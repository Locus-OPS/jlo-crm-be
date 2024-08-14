package th.co.locus.jlo.system.holiday;


import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.system.holiday.bean.HolidayModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Log4j2
@Service
public class HolidayServiceImpl extends BaseService implements HolidayService {

	@Override
	public ServiceResult<Page<HolidayModelBean>> getHolidayList(HolidayModelBean bean,PageRequest pageRequest) {
		try {
			return success(commonDao.selectPage("holiday.getHolidayList", bean, pageRequest));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<HolidayModelBean> getHolidayDetail(HolidayModelBean bean) {
		try {
			return success(commonDao.selectOne("holiday.getHolidayDetail", bean));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<HolidayModelBean> saveHoliday(HolidayModelBean bean) {
		try {
			int result=commonDao.insert("holiday.saveHoliday", bean);
			if(result>0) {
				return success(bean);
			}else {
				return fail("500","เกิดข้อผิดพลาด");
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<HolidayModelBean> editHoliday(HolidayModelBean bean) {
		try {
			int result=commonDao.update("holiday.editHoliday", bean);
			if(result>0) {
				return success(bean);
			}else {
				return fail("500","เกิดข้อผิดพลาด");
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<HolidayModelBean> deleteHoliday(HolidayModelBean bean) {
		try {
			int result=commonDao.delete("holiday.deleteHoliday", bean);
			if(result>0) {
				return success(bean);
			}else {
				return fail("500","เกิดข้อผิดพลาด");
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public void getHolidayType() {
		// TODO Auto-generated method stub
		
	}
	

}
