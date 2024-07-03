package th.co.locus.jlo.system.internationalization;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.system.internationalization.bean.InternationalizationModelBean;
import th.co.locus.jlo.system.internationalization.bean.SaveInternationalizationModelBean;
import th.co.locus.jlo.system.internationalization.bean.SearchInternationalizationModelBean;

@RestController
@RequestMapping("api/internationalization")
public class InternationalizationController extends BaseController {

    @Autowired
    private InternationalizationService internationalizationService;

    @ReadPermission
    @PostMapping(value = "/getInternationalizationList", produces = "application/json")
    public ApiPageResponse<List<InternationalizationModelBean>> getInternationalizationList(@RequestBody ApiPageRequest<SearchInternationalizationModelBean> request) {
        PageRequest pageRequest = getPageRequest(request);
        ServiceResult<Page<InternationalizationModelBean>> serviceResult = internationalizationService.getInternationalizationList(request.getData(), pageRequest);
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        } else {
            return ApiPageResponse.fail();
        }
    }

    @ReadPermission
    @PostMapping(value = "/getInternationalizationByMsgCode", produces = "application/json")
    public ApiResponse<List<InternationalizationModelBean>> getInternationalizationByMsgCode(@RequestBody ApiRequest<SearchInternationalizationModelBean> request) {
        ServiceResult<List<InternationalizationModelBean>> serviceResult = internationalizationService.getInternationalizationByMsgCode(request.getData());
        if (serviceResult.isSuccess()) {
            return ApiResponse.success(serviceResult.getResult());
        } else {
            return ApiResponse.fail();
        }
    }
    
    @WritePermission
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/saveInternationalization", produces = "application/json")
    public ApiResponse createInternationalization(@RequestBody ApiRequest<SaveInternationalizationModelBean> request) {
        ServiceResult serviceResult;
        serviceResult = internationalizationService.saveInternationalization(request.getData());
        if (serviceResult.isSuccess()) {
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.fail();
    }

    @WritePermission
    @PostMapping(value = "/deleteInternationalization", produces = "application/json")
    public ApiResponse<Integer> deleteInternationalization(@RequestBody ApiRequest<InternationalizationModelBean> request) {
        return ApiResponse.success(internationalizationService.deleteInternationalization(request.getData()).getResult());
    }
    
    @GetMapping(value = "/getTranslation/{language}.json", produces = "application/json")
    public Map<String, String> getTranslation(@PathVariable String language) {
        ServiceResult<List<InternationalizationModelBean>> serviceResult = internationalizationService.getTranslation(language.toUpperCase());       
        if (serviceResult.isSuccess()) {
        	Map<String, String> translageMap = serviceResult.getResult().stream().collect(Collectors.toMap(InternationalizationModelBean::getMsgCode, InternationalizationModelBean::getMsgValue));
            return translageMap;
        } else {
            return Map.of("error", "something wrong");
        }
    }
}
