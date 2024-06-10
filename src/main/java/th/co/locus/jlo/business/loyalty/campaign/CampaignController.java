package th.co.locus.jlo.business.loyalty.campaign;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.campaign.bean.CampaignModelBean;
import th.co.locus.jlo.business.loyalty.campaign.bean.SearchCampaignCriteriaModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;

@Api(value = "API for Loyalty Campaign Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/campaign")
public class CampaignController extends BaseController {
	
	@Autowired
	private CampaignService campaignService;
	
	@ReadPermission
	@ApiOperation(value = "Get Campaign List")
	@PostMapping(value = "/getCampaignList", produces = "application/json")
	public ApiPageResponse<List<CampaignModelBean>> getCampaignList(@RequestBody ApiPageRequest<SearchCampaignCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CampaignModelBean>> serviceResult = campaignService.getCampaignList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Campaign")
	@PostMapping(value = "/saveCampaign", produces = "application/json")
	public ApiResponse<CampaignModelBean> saveCampaign(@RequestBody ApiRequest<CampaignModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<CampaignModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = campaignService.updateCampaign(request.getData());
		} else {
			serviceResult = campaignService.createCampaign(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

}
