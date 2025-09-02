package th.co.locus.jlo.business.smartlink;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.smartlink.bean.SaveSmartLinkModelBean;
import th.co.locus.jlo.business.smartlink.bean.SmartLinkModelBean;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.codebook.CodebookService;
import th.co.locus.jlo.system.codebook.bean.CodebookModelBean;

/***
 * @author Apichat
 */

@Slf4j
@RestController
@RequestMapping("api/smartlink")
public class SmartLinkController extends BaseController {
    @Autowired
    private CodebookService codebookService;
    
    @Autowired
    private SmartLinkService smartLinkService;
    
    @Value("${landing.link}")
	private static String CONSENT_LINK;

	@Value("${landing.expire}")
	private static Integer EXPIRE_DATE;
    
    //WritePermission
    @PostMapping(value = "/generate", produces = "application/json")
    public ApiResponse<SmartLinkModelBean> generateSmartLink(@RequestBody ApiPageRequest<SaveSmartLinkModelBean> request) {
        SaveSmartLinkModelBean bean = request.getData();
        CommonUtil.nullifyObject(bean);
//        if(bean.getProfileId() == null || bean.getProfileId() == 0) {
//            return ApiResponse.fail("Profile ID is invalid.");
//        }
//        
        // Load code books.
        String[] codeTypes = { "LANDING_TEMPLATE", "LANDING_URL", "LANDING_EXPIRE" };
        ServiceResult<List<CodebookModelBean>> getCodebookResult = codebookService.searchCodebookByCodeTypes(codeTypes);
        loadLandingCodebookToBean(getCodebookResult.getResult(), bean);

        bean.setBuId(getBuId());
        bean.setCreatedBy(getUserId());
        bean.setUpdatedBy(getUserId());

        ServiceResult<SmartLinkModelBean> serviceResult = smartLinkService.createSmartLink(bean);
        if (serviceResult.isSuccess()) {
        	return ApiResponse.success(serviceResult.getResult());        	
        	// return smartLinkService.sendSmsEmail(bean, serviceResult.getResult());
        }
        return ApiResponse.fail();
    }
    
    private static SaveSmartLinkModelBean loadLandingCodebookToBean(List<CodebookModelBean> codebookList, SaveSmartLinkModelBean bean) {
		bean.setUrlLink(CONSENT_LINK);
		bean.setExpireDay(EXPIRE_DATE);

		if (codebookList != null && !codebookList.isEmpty()) {
			List<CodebookModelBean> urlCodebookList = codebookList.stream()
					.filter(cb -> cb.getCodeType().equals("LANDING_URL"))
					.collect(Collectors.toList());
			if (!urlCodebookList.isEmpty())
				bean.setUrlLink(urlCodebookList.getFirst().getEtc1());

			List<CodebookModelBean> expireCodebookList = codebookList.stream()
					.filter(cb -> cb.getCodeType().equals("LANDING_EXPIRE"))
					.collect(Collectors.toList());
			if (!expireCodebookList.isEmpty()) {
				try {
					bean.setExpireDay(Integer.parseInt(expireCodebookList.getFirst().getEtc1()));
				} catch (NumberFormatException e) {
					log.info(e.getMessage());
				}
			}
		}

		return bean;
	}
    
}
