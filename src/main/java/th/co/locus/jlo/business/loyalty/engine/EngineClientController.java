package th.co.locus.jlo.business.loyalty.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.loyalty.engine.bean.EngineCallModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.EngineCallResultModelBean;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.engine.client.EngineClient;
import th.co.locus.jlo.engine.client.response.ProcessTransactionResponse;
import th.co.locus.jlo.engine.client.response.ResponseMessage;

@Api(value = "Loyalty API for call engine ", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/engineClient")
@Slf4j
public class EngineClientController {

	@Autowired
	private EngineClient engineClient;

	@ApiOperation(value = "Call engine for process transaction")
	@PostMapping(value = "/callEngine", produces = "application/json")
	public ApiResponse<EngineCallResultModelBean> callEngine(@RequestBody ApiRequest<EngineCallModelBean> request) {
		EngineCallResultModelBean result = new EngineCallResultModelBean();
		try {
			ResponseMessage<ProcessTransactionResponse> engine = engineClient
					.processTransaction(request.getData().getTxnId());
			if (engine == null || !engine.isSuccess()) {
				log.info("Error");				
			} else {
				log.info("success");
			}
			result.setStatusCode(engine.getStatus().getStatusCode());
			result.setErrorCode(engine.getStatus().getErrorCode());
			result.setErrorMessage(engine.getStatus().getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (ApiResponse<EngineCallResultModelBean>) ApiResponse.success(result);
	}

}
