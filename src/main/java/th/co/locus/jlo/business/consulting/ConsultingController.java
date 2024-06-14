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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.WritePermission;

/**
 * 
 */
@Slf4j
@Api(value = "API for Consulting ", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/consulting")
public class ConsultingController extends BaseController {
	
	@Autowired
	private ConsultingService consultingService;
	
	
	@WritePermission
	@ApiOperation(value = "Create or Update Consulting")
	@PostMapping(value = "/processWalkinConsulting", produces = "application/json")
	public ApiResponse<ConsultingModelBean> processWalkinConsulting(@RequestBody ApiRequest<ConsultingModelBean> request) {
		StringUtil.nullifyObject(request.getData());		
		ServiceResult<ConsultingModelBean> serviceResult = new ServiceResult<ConsultingModelBean> ();
		try {
		Boolean isExistConsulting = true;
		
		if (!isExistConsulting) {
			
		}
		log.info(request.getData().toString());
		log.info("request.getData().getConsultingAction() "+request.getData().getId());
		
		if (request.getData().getId() != null) {
			ConsultingModelBean consultingData = request.getData();
			consultingData.setUpdatedBy(getUserId());
			consultingData.setStatusCd("02");//Finish
			serviceResult = consultingService.stopConsulting(consultingData);
			
		} else {
			ConsultingModelBean consultingData = request.getData();
			
			consultingData.setConsultingTypeCd("01");
			consultingData.setAgentState("Walk-In");
			consultingData.setReasonCode("Walk-In");	
			consultingData.setChannelCd("01");
			consultingData.setOwnerId(getRoleCode());
			consultingData.setStatusCd("01"); //In Progress
			
			consultingData.setBuId(getBuId());
			consultingData.setCreatedBy(getUserId());
			consultingData.setUpdatedBy(getUserId());
			serviceResult = consultingService.insertConsultingInital(consultingData);
		}
		
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
	
	
	@ApiOperation(value = "Get Consulting Data")
	@PostMapping(value = "/getConsultingData", produces = "application/json")
	public ApiResponse<ConsultingModelBean> getConsultingData(@RequestBody ApiRequest<ConsultingModelBean> request) {
		log.info("masterdata listMatGroup");
		ConsultingModelBean bean = request.getData();
		ServiceResult<ConsultingModelBean> serviceResult = consultingService.getConsultingData(bean);
		ConsultingModelBean finalResult = serviceResult.getResult();
		if(serviceResult.isSuccess()) {
			return ApiResponse.success(finalResult);
		}else {
			return ApiPageResponse.fail();			
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Update Consulting Infor")
    @PostMapping(value = "/updateConsulting", produces = "application/json")
    public ApiResponse<ConsultingModelBean> updateConsulting(@RequestBody ApiPageRequest<ConsultingModelBean> request) {
		StringUtil.nullifyObject(request.getData());
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
	
	
	@ApiOperation(value = "Get Consulting List")
    @PostMapping(value = "/getConsultingDataList", produces = "application/json")
    public ApiPageResponse<List<ConsultingModelBean>> getConsultingDataList(@RequestBody ApiPageRequest<ConsultingModelBean> request) {

        StringUtil.nullifyObject(request.getData());

        PageRequest pageRequest = getPageRequest(request);
        ServiceResult<Page<ConsultingModelBean>> serviceResult = consultingService.getConsultingDataList(request.getData(), pageRequest);
        
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(),
                    serviceResult.getResult().getTotalElements());
        } else {
            return ApiPageResponse.fail();
        }
    }
	
}