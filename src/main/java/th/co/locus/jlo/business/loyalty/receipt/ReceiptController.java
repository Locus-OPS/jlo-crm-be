package th.co.locus.jlo.business.loyalty.receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.engine.EngineTxnService;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionReceiptModelBean;
import th.co.locus.jlo.business.loyalty.receipt.bean.ReceiptModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;

@Api(value = "API for Loyalty earn receipt", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/receipt")
public class ReceiptController extends BaseController {

	@Autowired
	private EngineTxnService engineTxnService;

	@ApiOperation(value = "Save Loyalty Receipt")
	@PostMapping(value = "/saveReceipt", produces = "application/json")
	public ApiResponse<TransactionModelBean> saveReceipt(@RequestBody ApiPageRequest<ReceiptModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());

		// set receipt bean parameter
		TransactionReceiptModelBean bean = new TransactionReceiptModelBean();
		bean.setReceiptId(request.getData().getReceiptId());
		bean.setReceiptDate(request.getData().getReceiptDate());
		bean.setMemberId(request.getData().getMemberId());
		bean.setShopId(request.getData().getShopId());
		bean.setSpending(request.getData().getSpending());
		bean.setChannel("LOYALTY-WEB-ADMIN");

		ServiceResult<TransactionModelBean> serviceResult = engineTxnService.earnReceipt(bean);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail(serviceResult.getResponseDescription());
		}
	}
	
}
