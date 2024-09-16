package th.co.locus.jlo.mail.inbound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.SearchInboundReceiveMailBean;

@RestController
@RequestMapping("api/email-inbound")
public class InboundReceiveMailController extends BaseController {
	@Autowired
	private InboundReceiveMailService inboundEmailService;
	
	
	@ReadPermission
    @PostMapping(value = "/getEmailInboundList", produces = "application/json")
    public ApiPageResponse<List<InboundReceiveMailBean>> getEmailInboundList(@RequestBody ApiPageRequest<SearchInboundReceiveMailBean> request) {
    	request.getData().setBuId(getBuId());
    	PageRequest pageRequest = getPageRequest(request);
        ServiceResult<Page<InboundReceiveMailBean>> serviceResult = inboundEmailService.getEmailInboundList(request.getData(), pageRequest);
        
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        } else {
            return ApiPageResponse.fail();
        }
    }
}
