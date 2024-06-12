/**
 * 
 */
package th.co.locus.jlo.business.consulting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
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
}
