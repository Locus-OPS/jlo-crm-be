package th.co.locus.jlo.system.holiday;

import th.co.locus.jlo.system.holiday.bean.HolidayModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface HolidayService {
	public ServiceResult<Page<HolidayModelBean>> getHolidayList(HolidayModelBean bean,PageRequest pageRequest);
	public ServiceResult<HolidayModelBean> getHolidayDetail(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> saveHoliday(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> editHoliday(HolidayModelBean bean);
	public ServiceResult<HolidayModelBean> deleteHoliday(HolidayModelBean bean);
	public void getHolidayType();
}
