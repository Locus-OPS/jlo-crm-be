package th.co.locus.jlo.mail.inbound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.mail.inbound.att.InboundAttachmentReceiveMailService;
import th.co.locus.jlo.mail.inbound.att.bean.InboundAttachmentReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.SearchInboundReceiveMailBean;
import th.co.locus.jlo.system.file.FileService;

@RestController
@RequestMapping("api/email-inbound")
public class InboundReceiveMailController extends BaseController {
	@Autowired
	private InboundReceiveMailService inboundEmailService;
	
	@Autowired 
	private InboundAttachmentReceiveMailService attEmailService;
	
	@Autowired
	private FileService fileService;
	
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
	
	@ReadPermission
    @PostMapping(value = "/getEmailInboundDetailById", produces = "application/json")
    public ApiResponse<InboundReceiveMailBean> getEmailInboundDetailById(@RequestBody ApiPageRequest<InboundReceiveMailBean> request) {
		InboundReceiveMailBean bean  = request.getData();
    	
		ServiceResult<InboundReceiveMailBean> serviceResult = inboundEmailService.getEmailInboundData(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}else {
			return ApiResponse.fail();	
		}
		
      	
    }
	
	
	@PostMapping(value = "/getEmailInboundAttListById", produces = "application/json")
	public ApiResponse<List<InboundAttachmentReceiveMailBean>> getCaseAttachmentListByCaseNumber(
			@RequestBody ApiPageRequest<InboundReceiveMailBean> request) {
		request.getData().setBuId(getBuId());
	 
		ServiceResult<List<InboundAttachmentReceiveMailBean>> serviceResult = attEmailService.getEmailInboundAttListById(request.getData());

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@GetMapping(value = "/getDocumentAttPath")
	@ResponseBody
	public ResponseEntity<Resource> getDocumentAttPath(@RequestParam("filePath") String filePath) {
		Resource file = fileService.loadFile(filePath);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	 
}
