package th.co.locus.jlo.system.holiday;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.system.holiday.bean.HolidayModelBean;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
@Log4j2
@RestController
@RequestMapping("api/holiday")
public class HolidayController extends BaseController {
	@Autowired
	private HolidayService holidayService;
	
	@PostMapping(value="/getholidaylist",produces = "application/json")
	public ApiResponse<List<HolidayModelBean>> getHolidayList(@RequestBody ApiRequest<HolidayModelBean> request) {
		try {
			ServiceResult<List<HolidayModelBean>> serviceResult=this.holidayService.getHolidayList(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}else {
				return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getholidaydetail",produces = "application/json")
	public ApiResponse<HolidayModelBean> getHolidayDetail(@RequestBody ApiRequest<HolidayModelBean> request) {
		try {
			ServiceResult<HolidayModelBean> serviceResult=this.holidayService.getHolidayDetail(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult()); 
			}else {
				return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
			}
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/saveholiday",produces = "application/json")
	public ApiResponse<HolidayModelBean> saveHoliday(@RequestBody ApiRequest<HolidayModelBean> request) {
		try {
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<HolidayModelBean> serviceResult=this.holidayService.saveHoliday(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}else {
				return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/editholiday",produces = "application/json")
	public ApiResponse<HolidayModelBean> editHoliday(@RequestBody ApiRequest<HolidayModelBean> request) {
		try {
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<HolidayModelBean> serviceResult=this.holidayService.editHoliday(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}else {
				return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/deleteholiday",produces = "application/json")
	public ApiResponse<HolidayModelBean> deleteHoliday(@RequestBody ApiRequest<HolidayModelBean> request) {
		try {
			ServiceResult<HolidayModelBean> serviceResult=this.holidayService.deleteHoliday(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}else {
				return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
}
