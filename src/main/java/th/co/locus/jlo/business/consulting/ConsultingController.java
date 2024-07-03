/**
 * 
 */
package th.co.locus.jlo.business.consulting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

/**
 * 
 */
@Slf4j
@RestController
@RequestMapping("api/consulting")
public class ConsultingController extends BaseController {

	@Autowired
	private ConsultingService consultingService;

	@WritePermission
	@PostMapping(value = "/startWalkinConsulting", produces = "application/json")
	public ApiResponse<ConsultingModelBean> startWalkinConsulting(
			@RequestBody ApiRequest<ConsultingModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		ServiceResult<ConsultingModelBean> serviceResult = new ServiceResult<ConsultingModelBean>();
		try {
		 
			log.info(request.getData().toString());
			log.info("request.getData().getConsultingAction() " + request.getData().getId());

		 
				ConsultingModelBean consultingData = request.getData();
				
				consultingData.setStatusCd("01"); // In Progress
				consultingData.setConsultingTypeCd("01"); //Inbound				
				consultingData.setChannelCd("04");		 // WalkIn 04
				consultingData.setConsOwnerId(getUserId());
		
				consultingData.setBuId(getBuId());
				consultingData.setCreatedBy(getUserId());
				consultingData.setUpdatedBy(getUserId());
				serviceResult = consultingService.insertConsultingInital(consultingData);
			 
			if (serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ApiResponse.fail(e.getMessage());
		}

		return ApiResponse.fail(serviceResult.getResponseDescription());

	}
	
	@WritePermission
	@PostMapping(value = "/startPhoneConsulting", produces = "application/json")
	public ApiResponse<ConsultingModelBean> startPhoneConsulting(
			@RequestBody ApiRequest<ConsultingModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		ServiceResult<ConsultingModelBean> serviceResult = new ServiceResult<ConsultingModelBean>();
		try {
		 
			log.info(request.getData().toString());
			log.info("request.getData() " + request.getData());

		 
				ConsultingModelBean consultingData = request.getData();
				
				consultingData.setStatusCd("01"); // In Progress
				consultingData.setConsultingTypeCd("01"); //Inbound				
				consultingData.setChannelCd("01");		 // Phone 01
				consultingData.setConsOwnerId(getUserId());
				consultingData.setCustomerId(consultingData.getCustomerId());
				
				consultingData.setBuId(getBuId());
				consultingData.setCreatedBy(getUserId());
				consultingData.setUpdatedBy(getUserId());
				serviceResult = consultingService.insertConsultingInital(consultingData);
			 
			if (serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ApiResponse.fail(e.getMessage());
		}

		return ApiResponse.fail(serviceResult.getResponseDescription());

	}
	
	@WritePermission
	@PostMapping(value = "/updateStopConsulting", produces = "application/json")
	public ApiResponse<ConsultingModelBean> stopPhoneConsulting(@RequestBody ApiPageRequest<ConsultingModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		
		ConsultingModelBean bean = request.getData();
		bean.setContactId(bean.getCustomerId());
		bean.setBuId(getBuId());
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());
		bean.setStatusCd("02"); // Finished
		ServiceResult<ConsultingModelBean> serviceResult = consultingService.updateStopConsulting(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	

	@PostMapping(value = "/getConsultingData", produces = "application/json")
	public ApiResponse<ConsultingModelBean> getConsultingData(@RequestBody ApiRequest<ConsultingModelBean> request) {
		log.info("masterdata listMatGroup");
		ConsultingModelBean bean = request.getData();
		ServiceResult<ConsultingModelBean> serviceResult = consultingService.getConsultingData(bean);
		ConsultingModelBean finalResult = serviceResult.getResult();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(finalResult);
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@PostMapping(value = "/updateConsulting", produces = "application/json")
	public ApiResponse<ConsultingModelBean> updateConsulting(@RequestBody ApiPageRequest<ConsultingModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		
		ConsultingModelBean bean = request.getData();
		bean.setContactId(bean.getCustomerId());
		bean.setBuId(getBuId());
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());

		ServiceResult<ConsultingModelBean> serviceResult = consultingService.updateConsulting(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/updateConsultingBindingCustomer", produces = "application/json")
	public ApiResponse<ConsultingModelBean> updateConsultingBindingCustomer(@RequestBody ApiPageRequest<ConsultingModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		
		ConsultingModelBean bean = request.getData();
		bean.setContactId(bean.getCustomerId());
		bean.setBuId(getBuId());
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());

		ServiceResult<ConsultingModelBean> serviceResult = consultingService.updateConsultingBindingCustomer(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@PostMapping(value = "/getConsultingDataList", produces = "application/json")
	public ApiPageResponse<List<ConsultingModelBean>> getConsultingDataList(
			@RequestBody ApiPageRequest<ConsultingModelBean> request) {

		CommonUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ConsultingModelBean>> serviceResult = consultingService
				.getConsultingDataList(request.getData(), pageRequest);

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@PostMapping(value = "/getConsultingDataListByCustomerId", produces = "application/json")
	public ApiPageResponse<List<ConsultingModelBean>> getConsultingDataListByCustomerId(
			@RequestBody ApiPageRequest<ConsultingModelBean> request) {

		CommonUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ConsultingModelBean>> serviceResult = consultingService
				.getConsultingDataListByCustomerId(request.getData(), pageRequest);

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@PostMapping(value = "/getCaseUnderConsultingList", produces = "application/json")
	public ApiPageResponse<List<CaseModelBean>> getCaseUnderConsultingList(
			@RequestBody ApiPageRequest<ConsultingModelBean> request) {

		CommonUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CaseModelBean>> serviceResult = consultingService
				.getCaseUnderConsultingList(request.getData(), pageRequest);

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	

}
