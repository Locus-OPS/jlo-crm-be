package th.co.locus.jlo.system.holiday;

import th.co.locus.jlo.system.holiday.bean.HolidayModelBean;

import java.util.List;

import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface HolidayService {
	public ServiceResult<List<HolidayModelBean>> getHolidayList(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> getHolidayDetail(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> saveHoliday(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> editHoliday(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> deleteHoliday(HolidayModelBean bean);
	public void getHolidayType();
}
