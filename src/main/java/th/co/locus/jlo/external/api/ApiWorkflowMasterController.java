package th.co.locus.jlo.external.api;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.external.api.bean.ApiQueryWorkflowMasterResponse;
import th.co.locus.jlo.external.api.bean.ApiServiceResponse;

@Api
@RestController
@RequestMapping("workflow-service")
public class ApiWorkflowMasterController extends BaseController {
	
	
	@ApiOperation(value = "API for Query เรียก Workflow ตาม System และ Rule")
	@PostMapping(value = "/v1/workflow/getWorkflowBySystem", produces = "application/json")
	public ApiServiceResponse<ApiQueryWorkflowMasterResponse> getWorkflowBySystemAndAmount(
			@RequestBody ApiQueryWorkflowRequest request) throws ParseException {
		ApiQueryWorkflowMasterResponse response = new ApiQueryWorkflowMasterResponse();
		
		return ApiServiceResponse.success(response);
		
	}
	
	
//	@GetMapping("/by-system-and-amount")
//    public ResponseEntity<WorkflowDTO> getWorkflowBySystemAndAmount(
//            @RequestParam Integer systemId,
//            @RequestParam BigDecimal amount) {
//        WorkflowDTO workflow = workflowService.getWorkflowBySystemAndAmount(systemId, amount);
//        return ResponseEntity.ok(workflow);
//    }
	
}
