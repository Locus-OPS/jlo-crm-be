package th.co.locus.jlo.business.loyalty.reward;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.product.ProductService;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductOnHandModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductRedeemMethodModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductRedemptionTransactionModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.reward.bean.RewardModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.util.file.FileService;

@Api(value = "API for Loyalty Reward Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/reward")
public class RewardController extends BaseController {
	
	@Value("${attachment.path.reward_image}")
	private String rewardImagePath;
	
	@Autowired
	private RewardService rewardService;

	@Autowired
	private ProductService productService;

	@Autowired
	private FileService fileService;

	
	@ApiOperation(value = "Get Reward List")
	@PostMapping(value = "/getRewardList", produces = "application/json")
	public ApiPageResponse<List<RewardModelBean>> getRewardList(@RequestBody ApiPageRequest<SearchProductCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<RewardModelBean>> serviceResult = rewardService.getRewardList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	
	@ApiOperation(value = "Get Reward List without paging")
	@PostMapping(value = "/getRewardListWithoutPaging", produces = "application/json")
	public ApiResponse<List<RewardModelBean>> getRewardListWithoutPaging(@RequestBody ApiRequest<SearchProductCriteriaModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		ServiceResult<List<RewardModelBean>> serviceResult = rewardService.getRewardList(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@ApiOperation(value = "Create or Update Reward")
	@PostMapping(value = "/saveReward", produces = "application/json")
	public ApiResponse<RewardModelBean> saveReward(@RequestBody ApiRequest<RewardModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<RewardModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getProductId() != null) {
			serviceResult = rewardService.updateReward(request.getData());
		} else {
			serviceResult = rewardService.createReward(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@ApiOperation(value = "Upload Reward Image")
	@PostMapping(value = "/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("productId") Long productId) {
		String message = "";
		try {
			String fileName = productId + "_" + CommonUtil.getFileExtension(file);
			fileService.saveFile(file, rewardImagePath, fileName);
			rewardService.updateRewardImage(fileName, productId);
			return ResponseEntity.status(HttpStatus.OK).body(fileName);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	
	@ApiOperation(value = "Get Reward Image")
	@GetMapping(value = "/reward_image/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getRewardImage(@PathVariable String fileName) {
		Resource file = fileService.loadFile(rewardImagePath + File.separator + fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	
	@ApiOperation(value = "Get Reward OnHand List")
	@PostMapping(value = "/getRewardOnHandList", produces = "application/json")
	public ApiPageResponse<List<LoyaltyProductOnHandModelBean>> getRewardOnHandList(@RequestBody ApiPageRequest<LoyaltyProductOnHandModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<LoyaltyProductOnHandModelBean>> serviceResult = rewardService.getRewardOnHandList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ApiOperation(value = "Create or Update Reward OnHand")
	@PostMapping(value = "/saveRewardOnHand", produces = "application/json")
	public ApiResponse<LoyaltyProductOnHandModelBean> saveRewardOnHand(
			@RequestBody ApiRequest<LoyaltyProductOnHandModelBean> request) {

		LoyaltyProductOnHandModelBean loyaltyProductOnHandModelBean = request.getData();
		StringUtil.nullifyObject(loyaltyProductOnHandModelBean);

		loyaltyProductOnHandModelBean.setCreatedBy(getUserId());
		loyaltyProductOnHandModelBean.setUpdatedBy(getUserId());

		ServiceResult<LoyaltyProductOnHandModelBean> serviceResult = rewardService
				.createRewardOnHand(loyaltyProductOnHandModelBean);

		if (serviceResult.isSuccess()) {
			ServiceResult<ProductModelBean> setInventoryBalanceResult = null;
			if ("ADD".equals(loyaltyProductOnHandModelBean.getOnhandType())) {
				setInventoryBalanceResult = productService.addInventoryBalance(
						loyaltyProductOnHandModelBean.getProductId(), loyaltyProductOnHandModelBean.getAmount());
			} else { // Subtract
				setInventoryBalanceResult = productService.subtractInventoryBalance(
						loyaltyProductOnHandModelBean.getProductId(), loyaltyProductOnHandModelBean.getAmount());
			}

			if (setInventoryBalanceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
		}
		return ApiResponse.fail();
	}

	@ApiOperation(value = "Delete Reward OnHand")
	@PostMapping(value = "/deleteRewardOnHand", produces = "application/json")
	public ApiResponse<Integer> deleteRewardOnHand(@RequestBody ApiRequest<LoyaltyProductOnHandModelBean> request) {
		return ApiResponse.success(rewardService.deleteRewardOnHand(request.getData()).getResult());
	}

	
	@ApiOperation(value = "Get Reward Balance")
	@PostMapping(value = "/getRewardBalance", produces = "application/json")
	public ApiResponse<ServiceResult<Long>> getRewardBalance(@RequestBody ApiRequest<Long> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<Long> serviceResult = rewardService.getRewardBalance(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult);
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Reward Redemption Transaction")
	@PostMapping(value = "/getRewardRedemptionTransaction", produces = "application/json")
	public ApiResponse<ServiceResult<Long>> getRewardRedemptionTransaction(@RequestBody ApiRequest<Long> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<Long> serviceResult = rewardService.getRewardRedemptionTransaction(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult);
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	
	//#################### Redeem Method ####################
		
	@ApiOperation(value = "Get Redeem Method List")
	@PostMapping(value = "/getRedeemMethodList", produces = "application/json")
	public ApiPageResponse<List<LoyaltyProductRedeemMethodModelBean>> getRedeemMethodList(@RequestBody ApiPageRequest<LoyaltyProductRedeemMethodModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<LoyaltyProductRedeemMethodModelBean>> serviceResult = rewardService.getRedeemMethodList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ApiOperation(value = "Create or Update Redeem Method")
	@PostMapping(value = "/saveRedeemMethod", produces = "application/json")
	public ApiResponse<LoyaltyProductRedeemMethodModelBean> saveRedeemMethod(
			@RequestBody ApiRequest<LoyaltyProductRedeemMethodModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<LoyaltyProductRedeemMethodModelBean> serviceResult;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getProductRedeemMethodId() != null) {
			serviceResult = rewardService.updateRedeemMethod(request.getData());
		} else {
			serviceResult = rewardService.createRedeemMethod(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@ApiOperation(value = "Delete Redeem Method")
	@PostMapping(value = "/deleteRedeemMethod", produces = "application/json")
	public ApiResponse<Integer> deleteRedeemMethod(
			@RequestBody ApiRequest<LoyaltyProductRedeemMethodModelBean> request) {
		return ApiResponse.success(rewardService.deleteRedeemMethod(request.getData()).getResult());
	}

	//#################### Redemption Transaction ####################
	@ApiOperation(value = "Get Redemption Transaction List")
	@PostMapping(value = "/getRedemptionTransactionList", produces = "application/json")
	public ApiPageResponse<List<LoyaltyProductRedemptionTransactionModelBean>> getRedemptionTransactionList(@RequestBody ApiPageRequest<LoyaltyProductRedemptionTransactionModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<LoyaltyProductRedemptionTransactionModelBean>> serviceResult = rewardService.getRedemptionTransactionList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

}


