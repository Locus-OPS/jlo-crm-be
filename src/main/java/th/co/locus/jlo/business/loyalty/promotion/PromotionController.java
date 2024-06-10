package th.co.locus.jlo.business.loyalty.promotion;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.customer.bean.MemberMasterData;
import th.co.locus.jlo.business.loyalty.promotion.bean.ProductModalModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionAttributeModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionMemberModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionShopModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionUtils;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleActionModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.common.util.FileUtil;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;

@Api(value = "API for Loyalty Program Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/loyalty/promotion")
public class PromotionController extends BaseController {

	@Autowired
	private PromotionService promotionService;

	/* ################################ Program ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program List")
	@PostMapping(value = "/getPromotionList", produces = "application/json")
	public ApiPageResponse<List<PromotionModelBean>> getPromotionList(
			@RequestBody ApiPageRequest<PromotionModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PromotionModelBean>> serviceResult = promotionService.getPromotionList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Program")
	@PostMapping(value = "/savePromotion", produces = "application/json")
	public ApiResponse<PromotionModelBean> savePromotion(@RequestBody ApiRequest<PromotionModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<PromotionModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if(request.getData().getDeleteFlag() == null) {
			request.getData().setDeleteFlag("N");
		}
		if (request.getData().getCreatedDate() != null) {
			serviceResult = promotionService.updatePromotion(request.getData());
		} else {
			serviceResult = promotionService.createPromotion(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "No record is updated or inserted.");
	}

	@WritePermission
	@ApiOperation(value = "Delete Program")
	@PostMapping(value = "/deletePromotion", produces = "application/json")
	public ApiResponse<Integer> deletePromotion(@RequestBody ApiRequest<PromotionModelBean> request) {

		ServiceResult<Integer> serviceResult = promotionService.deletePromotion(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("The promotion (" + request.getData().getPromotionId() + ") cannot be deleted.");
	}

	/* ################################ Rule ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Rule List")
	@PostMapping(value = "/getRuleList", produces = "application/json")
	public ApiPageResponse<List<RuleModelBean>> getRuleList(@RequestBody ApiPageRequest<RuleModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<RuleModelBean>> serviceResult = promotionService.getRuleList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Rule")
	@PostMapping(value = "/saveRule", produces = "application/json")
	public ApiResponse<RuleModelBean> saveRule(@RequestBody ApiRequest<RuleModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<RuleModelBean> serviceResult = null;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());

		if (request.getData().getCreatedDate() != null) { // update
			serviceResult = promotionService.updateRule(request.getData());
		} else {
			ServiceResult<Long> seqSR = promotionService.getNextSeq(request.getData());

			if (seqSR.isSuccess()) {
				request.getData().setSeq(seqSR.getResult());
				serviceResult = promotionService.createRule(request.getData());
			}
		}

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "No record is updated or inserted.");
	}

	@WritePermission
	@ApiOperation(value = "Delete Rule")
	@PostMapping(value = "/deleteRule", produces = "application/json")
	public ApiResponse<Boolean> deleteRule(@RequestBody ApiRequest<RuleModelBean> request) {

		// update sequence before delete in ServiceImpl with @Transactional
		ServiceResult<Boolean> serviceResult = promotionService.deleteRule(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "The rule (" + request.getData().getRuleId() + ") cannot be deleted.");

	}

	/*
	 * ################################ Rule Sequence
	 * ###############################
	 */
	@WritePermission
	@ApiOperation(value = "Update Rule Sequence")
	@PostMapping(value = "/updateSeq", produces = "application/json")
	public ApiResponse<Boolean> updateSeq(@RequestBody ApiRequest<RuleModelBean> request) {

		ServiceResult<Boolean> serviceResult = promotionService.updateSeq(request.getData());
		return ApiResponse.success(serviceResult.getResult());
	}

	/*
	 * ################################ Rule Criteria
	 * ###############################
	 */
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Rule Criteria List")
	@PostMapping(value = "/getRuleCriteriaList", produces = "application/json")
	public ApiPageResponse<List<RuleCriteriaModelBean>> getRuleCriteriaList(
			@RequestBody ApiPageRequest<RuleCriteriaModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<RuleCriteriaModelBean>> serviceResult = promotionService
				.getRuleCriteriaList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Rule Criteria")
	@PostMapping(value = "/saveRuleCriteria", produces = "application/json")
	public ApiResponse<RuleCriteriaModelBean> saveRuleCriteria(@RequestBody ApiRequest<RuleCriteriaModelBean> request) {

		RuleCriteriaModelBean bean = request.getData();

		if (PromotionUtils.COND_IS_EMPTY.equals(bean.getSrcCondition())) {
			bean.setCompareToOv(null);
			bean.setDscObj(null);
			bean.setDscAttr(null);
			bean.setDscOperator(null);
			bean.setDscValue(null);
		} else {
			if ("O".equals(bean.getCompareToOv())) {
				bean.setDscValue(bean.getDscObjValue());
			} else {
				bean.setDscObj(null);
				bean.setDscAttr(null);
				bean.setDscOperator(null);
			}
		}

		// StringUtil.nullifyObject(request.getData());

		ServiceResult<RuleCriteriaModelBean> serviceResult;
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());
		if (bean.getCreatedDate() != null) {
			serviceResult = promotionService.updateRuleCriteria(bean);
		} else {
			serviceResult = promotionService.createRuleCriteria(bean);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "No rule Criteria is updated or inserted.");
	}

	@WritePermission
	@ApiOperation(value = "Delete Rule Criteria")
	@PostMapping(value = "/deleteRuleCriteria", produces = "application/json")
	public ApiResponse<Boolean> deleteRuleCriteria(@RequestBody ApiRequest<RuleCriteriaModelBean> request) {

		ServiceResult<Boolean> serviceResult = promotionService.deleteRuleCriteria(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "The rule Criteria (" + request.getData().getRuleId() + ") cannot be deleted.");
	}

	/*
	 * ################################ Rule Action ###############################
	 */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Rule Action List")
	@PostMapping(value = "/getRuleActionList", produces = "application/json")
	public ApiPageResponse<List<RuleActionModelBean>> getRuleActionList(
			@RequestBody ApiPageRequest<RuleActionModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<RuleActionModelBean>> serviceResult = promotionService.getRuleActionList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Rule Action")
	@PostMapping(value = "/saveRuleAction", produces = "application/json")
	public ApiResponse<RuleActionModelBean> saveRuleAction(@RequestBody ApiRequest<RuleActionModelBean> request) {

		RuleActionModelBean bean = request.getData();

		if ("Earning".equals(bean.getActionType()) || "Burning".equals(bean.getActionType())) {
			bean.setUpdObj(null);
			bean.setUpdAttr(null);
			bean.setUpdOperator(null);
			
			if(bean.getPointOperator() == null) {
				bean.setPointOperator("");
			}
			
			if(bean.getPointValue() == null) {
				bean.setPointValue("");
			}
		} else {
			bean.setPointType(null);
			bean.setPointOperator(null);
			bean.setPointValue(null);
			bean.setPointExpireUnit(null);
			bean.setPointExpirePeriod(null);
		}
		// StringUtil.nullifyObject(request.getData());

		ServiceResult<RuleActionModelBean> serviceResult;
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());

		if (bean.getCreatedDate() != null) {
			serviceResult = promotionService.updateRuleAction(bean);
		} else {
			serviceResult = promotionService.createRuleAction(bean);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "No rule Action is updated or inserted.");
	}

	@WritePermission
	@ApiOperation(value = "Delete Rule Action")
	@PostMapping(value = "/deleteRuleAction", produces = "application/json")
	public ApiResponse<Boolean> deleteRuleAction(@RequestBody ApiRequest<RuleActionModelBean> request) {

		ServiceResult<Boolean> serviceResult = promotionService.deleteRuleAction(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "The rule Action (" + request.getData().getRuleId() + ") cannot be deleted.");
	}

	/* ################################ Product ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Product List")
	@PostMapping(value = "/getProductList", produces = "application/json")
	public ApiPageResponse<List<ProductModelBean>> getProductList(
			@RequestBody ApiPageRequest<ProductModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProductModelBean>> serviceResult = promotionService.getProductList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	// Strange... they should have a product list without selected products....
	// Should exclude the products in the selected list....
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program All Product List")
	@PostMapping(value = "/getAllProductList", produces = "application/json")
	public ApiPageResponse<List<ProductModalModelBean>> getAllProductList(
			@RequestBody ApiPageRequest<ProductModalModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProductModalModelBean>> serviceResult = promotionService.getAllProductList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	/*
	 * @ApiOperation(value = "Create or Update Loyalty Program Product")
	 * 
	 * @PostMapping(value = "/saveProduct", produces = "application/json")
	 * public ApiResponse<ProductModalModelBean> saveProduct(@RequestBody
	 * ApiRequest<ProductModelBean> request) {
	 * 
	 * StringUtil.nullifyObject(request.getData());
	 * 
	 * ServiceResult<ProductModelBean> serviceResult;
	 * request.getData().setCreatedBy(getUserId());
	 * request.getData().setUpdatedBy(getUserId()); if
	 * (request.getData().getCreatedDate() != null) { serviceResult =
	 * promotionService.updateProduct(request.getData()); } else { serviceResult =
	 * promotionService.createProduct(request.getData()); } if
	 * (serviceResult.isSuccess()) { return
	 * ApiResponse.success(serviceResult.getResult()); } return
	 * ApiResponse.fail("500", "No record is updated or inserted."); }
	 */

	@WritePermission
	@ApiOperation(value = "Insert Loyalty Program Product")
	@PostMapping(value = "/insertPromotionProduct", produces = "application/json")
	public ApiResponse<Integer> insertPromotionProduct(@RequestBody ApiRequest<ProductModelBean[]> request) {

		StringUtil.nullifyObject(request.getData());

		List<ProductModelBean> productModalList = new ArrayList<>(Arrays.asList(request.getData()));
		productModalList.stream().forEach(item -> item.setCreatedBy(getUserId()));
		productModalList.stream().forEach(item -> item.setUpdatedBy(getUserId()));

		ServiceResult<Integer> serviceResult = promotionService
				.insertPromotionProduct(productModalList.toArray(new ProductModelBean[productModalList.size()]));

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}

	}

	@WritePermission
	@ApiOperation(value = "Delete Program Product")
	@PostMapping(value = "/deleteProduct", produces = "application/json")
	public ApiResponse<Integer> deleteProduct(@RequestBody ApiRequest<ProductModelBean> request) {

		ServiceResult<Integer> serviceResult = promotionService.deleteProduct(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("The Product (" + request.getData().getPromotionProductId() + ") cannot be deleted.");
	}

	@WritePermission
	@ApiOperation(value = "Upload Program Product")
	@PostMapping(value = "/uploadExcel", produces = "application/json")
	public ApiResponse<HashMap<String, Integer>> uploadExcel(@RequestBody HashMap<String, String> request) throws Exception{

		/*
		 * It is strange that the logic does not check the duplicate in the selected
		 * product list. Need to check with BA and other developers.
		 */

		InputStream is = null;
		String promotionId = request.get("promotionId");

		// 1. Read file
		is = FileUtil.readInputStream(request.get("base64File"));
		HashMap<Integer, List<String>> sheet = FileUtil.readExcelDataByFilePath(is);

		// 2. Get data in file
		List<Entry<Integer, List<String>>> entries = CommonUtil.convertMapToList(sheet);
		List<ProductModalModelBean> productInFileList = convertDataToList(entries);

		// 3. Check data have in master
		List<ProductModalModelBean> productInFileAndInMasterList = new ArrayList<ProductModalModelBean>();
		productInFileAndInMasterList = checkProductHaveMaster(productInFileList);

		// 4. find item code
		// This is important. It will convert ProductModalModelBean -> ProductModelBean to be inserted under the promotion.
		List<ProductModelBean> confirmedPromotionProductList = findItemCode(productInFileAndInMasterList, promotionId, getUserId());

		// 5. insert promotion product
		ServiceResult<Integer> saveResult = promotionService.insertPromotionProduct(confirmedPromotionProductList.stream().toArray(ProductModelBean[]::new));

		// Summary
		HashMap<String, Integer> mapResult = new HashMap<>();
		mapResult.put("totalRecord", sheet.size());
		mapResult.put("success", productInFileAndInMasterList.size());
		mapResult.put("error", (sheet.size() - productInFileAndInMasterList.size()));

		/*
		 * ServiceResult<Integer> serviceResult =
		 * promotionService.deleteProduct(request.getData()); if
		 * (serviceResult.isSuccess()) { return
		 * ApiResponse.success(serviceResult.getResult()); } return
		 * ApiResponse.fail("The Product ("+ request.getData().getPromotionProductId()
		 * +") cannot be deleted.");
		 */
		// return ApiResponse.success(ProductExcelUploadResultModelBean);
		return ApiResponse.success(mapResult);

	}

	private List<ProductModalModelBean> convertDataToList(List<Entry<Integer, List<String>>> entries) {
		List<ProductModalModelBean> listProduct = new ArrayList<ProductModalModelBean>();

		for (Entry<Integer, List<String>> entry : entries) {

			ProductModalModelBean proBean = new ProductModalModelBean();
			List<String> list = entry.getValue();

			proBean.setItem(list.get(0).toString());
			proBean.setCategoryId(Long.valueOf(list.get(1)));
			listProduct.add(proBean);
		}
		return listProduct;
	}

	private List<ProductModalModelBean> checkProductHaveMaster(List<ProductModalModelBean> productInFileList){
		// Search product all
		ServiceResult<List<ProductModalModelBean>> productInMasterListResult = promotionService.getProductMasterList(new ProductModalModelBean());
		List<ProductModalModelBean> productInMasterList = new ArrayList<ProductModalModelBean>();

		
		 if(productInMasterListResult.isSuccess()) { productInMasterList = productInMasterListResult.getResult(); }
		 

		List<ProductModalModelBean> productInFileAndInMasterList = new ArrayList<ProductModalModelBean>();
		productInFileAndInMasterList = compareOrderLists(productInMasterList, productInFileList);

		return productInFileAndInMasterList;
	}

	private static List<ProductModalModelBean> compareOrderLists(List<ProductModalModelBean> productInMasterList,
			List<ProductModalModelBean> productInFileList) {
		List<ProductModalModelBean> productInFileAndInMasterList = new ArrayList<ProductModalModelBean>();
		productInMasterList.forEach(
				p -> productInFileList.stream().filter(p1 -> p.getItem().equals(p1.getItem())).forEach(productInFileAndInMasterList::add));
		return productInFileAndInMasterList;

	}

	private List<ProductModelBean> findItemCode(List<ProductModalModelBean> productInFileAndInMasterList, String promotionId,
			String userId) {
		List<ProductModelBean> list = new ArrayList<ProductModelBean>();

		try {

			for (ProductModalModelBean productModalModelBean : productInFileAndInMasterList) {
				ServiceResult<List<ProductModalModelBean>> productModalDetailResult = promotionService
						.getProductMasterList(productModalModelBean);
				if (productModalDetailResult.isSuccess()) {
					ProductModelBean productModelBean = new ProductModelBean();
					productModelBean.setProductCode(productModalDetailResult.getResult().get(0).getItemId());
					list.add(productModelBean);
				}
			}

			list.forEach(f -> f.setCreatedBy(userId));
			list.forEach(f -> f.setPromotionId(Long.valueOf(promotionId)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/* ################################ Member ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Member List")
	@PostMapping(value = "/getMemberList", produces = "application/json")
	public ApiPageResponse<List<PromotionMemberModelBean>> getMemberList(
			@RequestBody ApiPageRequest<PromotionMemberModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PromotionMemberModelBean>> serviceResult = promotionService.getMemberList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Member Master List")
	@PostMapping(value = "/getMemberMasterList", produces = "application/json")
	public ApiPageResponse<List<MemberMasterData>> getMemberMasterList(
			@RequestBody ApiPageRequest<MemberMasterData> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<MemberMasterData>> serviceResult = promotionService.getMemberMasterList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	// Strange... they should have a member list without selected members....
	// Should exclude the members in the selected list....
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program All Member List")
	@PostMapping(value = "/getAllMemberList", produces = "application/json")
	public ApiPageResponse<List<MemberData>> getAllMemberList(
			@RequestBody ApiPageRequest<MemberData> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<MemberData>> serviceResult = promotionService.getAllMemberList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Insert Loyalty Program Member")
	@PostMapping(value = "/insertPromotionMember", produces = "application/json")
	public ApiResponse<Integer> insertPromotionMember(@RequestBody ApiRequest<PromotionMemberModelBean[]> request) {

		StringUtil.nullifyObject(request.getData());

		List<PromotionMemberModelBean> promotionMemberlList = new ArrayList<>(Arrays.asList(request.getData()));
		promotionMemberlList.stream().forEach(item -> item.setCreatedBy(getUserId()));
		promotionMemberlList.stream().forEach(item -> item.setUpdatedBy(getUserId()));

		ServiceResult<Integer> serviceResult = promotionService
				.insertPromotionMember(promotionMemberlList.toArray(new PromotionMemberModelBean[promotionMemberlList.size()]));

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}

	}

	@WritePermission
	@ApiOperation(value = "Delete Program Member")
	@PostMapping(value = "/deleteMember", produces = "application/json")
	public ApiResponse<Integer> deleteMember(@RequestBody ApiRequest<PromotionMemberModelBean> request) {

		ServiceResult<Integer> serviceResult = promotionService.deleteMember(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("The Member (" + request.getData().getPromotionMemberId() + ") cannot be deleted.");
	}

	@WritePermission
	@ApiOperation(value = "Upload Program Member")
	@PostMapping(value = "/uploadPromotionMemberExcel", produces = "application/json")
	public ApiResponse<HashMap<String, Integer>> uploadPromotionMemberExcel(@RequestBody HashMap<String, String> request) throws Exception{

		/*
		 * It is strange that the logic does not check the duplicate in the selected
		 * member list. Need to check with BA and other developers.
		 */

		InputStream is = null;
		String promotionId = request.get("promotionId");

		// 1. Read file
		is = FileUtil.readInputStream(request.get("base64File"));
		HashMap<Integer, List<String>> sheet = FileUtil.readExcelDataByFilePath(is);

		// 2. Get data in file
		List<Entry<Integer, List<String>>> entries = CommonUtil.convertMapToList(sheet);
		List<MemberData> memberInFileList = convertMemberDataToList(entries);

		// 3. Check data have in master
		List<MemberData> memberInFileAndInMasterList = new ArrayList<MemberData>();
		memberInFileAndInMasterList = checkMemberHaveMaster(memberInFileList);

		// 4. find item code
		// This is important. It will convert MemberModalModelBean -> MemberModelBean to be inserted under the promotion.
		List<PromotionMemberModelBean> confirmedPromotionMemberList = findMemberId(memberInFileAndInMasterList, promotionId, getUserId());

		// 5. insert promotion member
		ServiceResult<Integer> saveResult = promotionService.insertPromotionMember(confirmedPromotionMemberList.stream().toArray(PromotionMemberModelBean[]::new));

		// Summary
		HashMap<String, Integer> mapResult = new HashMap<>();
		mapResult.put("totalRecord", sheet.size());
		mapResult.put("success", memberInFileAndInMasterList.size());
		mapResult.put("error", (sheet.size() - memberInFileAndInMasterList.size()));

		return ApiResponse.success(mapResult);

	}

	/*
	 * private static <K, V> List<Map.Entry<K, V>> convertToList(Map<K, V> map) {
	 * return map.entrySet().stream().collect(Collectors.toList()); }
	 */

	private List<MemberData> convertMemberDataToList(List<Entry<Integer, List<String>>> entries) {
		List<MemberData> listMember = new ArrayList<MemberData>();

		for (Entry<Integer, List<String>> entry : entries) {

			MemberData memBean = new MemberData();
			List<String> list = entry.getValue();

			memBean.setTitle(list.get(0).toString());
			memBean.setFirstName(list.get(1).toString());
			memBean.setLastName(list.get(2).toString());
			listMember.add(memBean);
		}
		return listMember;
	}

	private List<MemberData> checkMemberHaveMaster(List<MemberData> memberInFileList){
		// It should not be like this due to performance concern. Need to modify the source code and logic.
		// I will comment them out first and simply return the members in the file.
		
		/*
		 * ServiceResult<List<MemberData>> memberInMasterListResult =
		 * promotionService.getMemberMasterList(new MemberData()); List<MemberData>
		 * memberInMasterList = new ArrayList<MemberData>();
		 * 
		 * 
		 * if(memberInMasterListResult.isSuccess()) { memberInMasterList =
		 * memberInMasterListResult.getResult(); }
		 * 
		 * 
		 * List<MemberData> memberInFileAndInMasterList = new ArrayList<MemberData>();
		 * memberInFileAndInMasterList = compareMemberLists(memberInMasterList,
		 * memberInFileList);
		 * 
		 * return memberInFileAndInMasterList;
		 */
		return memberInFileList;
	}

	private static List<MemberData> compareMemberLists(List<MemberData> memberInMasterList,
			List<MemberData> memberInFileList) {
		List<MemberData> memberInFileAndInMasterList = new ArrayList<MemberData>();
		memberInMasterList.forEach(
				p -> memberInFileList.stream().filter(p1 -> p.getTitle().equals(p1.getTitle()) && p.getFirstName().equals(p1.getFirstName())  
              		  && p.getLastName().equals(p1.getLastName())).forEach(memberInFileAndInMasterList::add));
		return memberInFileAndInMasterList;

	}

	private List<PromotionMemberModelBean> findMemberId(List<MemberData> memberInFileAndInMasterList, String promotionId,
			String userId) {
		List<PromotionMemberModelBean> list = new ArrayList<PromotionMemberModelBean>();

		try {

			for (MemberData memberData : memberInFileAndInMasterList) {
				ServiceResult<List<MemberData>> memberDataDetailResult = promotionService
						.getMemberMasterList(memberData);
				if (memberDataDetailResult.isSuccess()) {
					PromotionMemberModelBean promotionMemberModelBean = new PromotionMemberModelBean();
					
					promotionMemberModelBean.setMemberId(Long.valueOf(memberDataDetailResult.getResult().get(0).getMemberId()));
					promotionMemberModelBean.setCardNo(memberDataDetailResult.getResult().get(0).getMemberCardNoData().getMemberCardNo());
					
					list.add(promotionMemberModelBean);
				}
			}

			list.forEach(f -> f.setCreatedBy(userId));
			list.forEach(f -> f.setPromotionId(Long.valueOf(promotionId)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/* ################################ Program ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Program List")
	@PostMapping(value = "/getShopList", produces = "application/json")
	public ApiPageResponse<List<PromotionShopModelBean>> getShopList(
			@RequestBody ApiPageRequest<PromotionShopModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PromotionShopModelBean>> serviceResult = promotionService.getShopList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Program Master List")
	@PostMapping(value = "/getShopMasterList", produces = "application/json")
	public ApiPageResponse<List<ShopModelBean>> getShopMasterList(
			@RequestBody ApiPageRequest<SearchShopCriteriaModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ShopModelBean>> serviceResult = promotionService.getShopMasterList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	// Strange... they should have a shop list without selected shops....
	// Should exclude the shops in the selected list....
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program All Program List")
	@PostMapping(value = "/getAllShopList", produces = "application/json")
	public ApiPageResponse<List<ShopModelBean>> getAllShopList(
			@RequestBody ApiPageRequest<ShopModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ShopModelBean>> serviceResult = promotionService.getAllShopList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Insert Loyalty Program Program")
	@PostMapping(value = "/insertPromotionShop", produces = "application/json")
	public ApiResponse<Integer> insertPromotionShop(@RequestBody ApiRequest<PromotionShopModelBean[]> request) {

		StringUtil.nullifyObject(request.getData());

		List<PromotionShopModelBean> promotionShoplList = new ArrayList<>(Arrays.asList(request.getData()));
		promotionShoplList.stream().forEach(item -> item.setCreatedBy(getUserId()));
		promotionShoplList.stream().forEach(item -> item.setUpdatedBy(getUserId()));

		ServiceResult<Integer> serviceResult = promotionService
				.insertPromotionShop(promotionShoplList.toArray(new PromotionShopModelBean[promotionShoplList.size()]));

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}

	}

	@WritePermission
	@ApiOperation(value = "Delete Program Program")
	@PostMapping(value = "/deleteShop", produces = "application/json")
	public ApiResponse<Integer> deleteShop(@RequestBody ApiRequest<PromotionShopModelBean> request) {

		ServiceResult<Integer> serviceResult = promotionService.deleteShop(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("The Program (" + request.getData().getShopId() + ") cannot be deleted.");
	}

	@WritePermission
	@ApiOperation(value = "Upload Program Program")
	@PostMapping(value = "/uploadPromotionShopExcel", produces = "application/json")
	public ApiResponse<HashMap<String, Integer>> uploadPromotionShopExcel(@RequestBody HashMap<String, String> request) throws Exception{

		/*
		 * It is strange that the logic does not check the duplicate in the selected
		 * shop list. Need to check with BA and other developers.
		 */

		InputStream is = null;
		String promotionId = request.get("promotionId");

		// 1. Read file
		is = FileUtil.readInputStream(request.get("base64File"));
		HashMap<Integer, List<String>> sheet = FileUtil.readExcelDataByFilePath(is);

		// 2. Get data in file
		List<Entry<Integer, List<String>>> entries = CommonUtil.convertMapToList(sheet);
		List<ShopModelBean> shopInFileList = convertShopModelBeanToList(entries);

		// 3. Check data have in master
		List<ShopModelBean> shopInFileAndInMasterList = new ArrayList<ShopModelBean>();
		shopInFileAndInMasterList = checkShopHaveMaster(shopInFileList);

		// 4. find item code
		// This is important. It will convert ShopModalModelBean -> ShopModelBean to be inserted under the promotion.
		List<PromotionShopModelBean> confirmedPromotionShopList = findShopId(shopInFileAndInMasterList, promotionId, getUserId());

		// 5. insert promotion shop
		ServiceResult<Integer> saveResult = promotionService.insertPromotionShop(confirmedPromotionShopList.stream().toArray(PromotionShopModelBean[]::new));

		// Summary
		HashMap<String, Integer> mapResult = new HashMap<>();
		mapResult.put("totalRecord", sheet.size());
		mapResult.put("success", shopInFileAndInMasterList.size());
		mapResult.put("error", (sheet.size() - shopInFileAndInMasterList.size()));

		return ApiResponse.success(mapResult);

	}

	/*
	 * private static <K, V> List<Map.Entry<K, V>> convertToList(Map<K, V> map) {
	 * return map.entrySet().stream().collect(Collectors.toList()); }
	 */

	private List<ShopModelBean> convertShopModelBeanToList(List<Entry<Integer, List<String>>> entries) {
		List<ShopModelBean> listShop = new ArrayList<ShopModelBean>();

		for (Entry<Integer, List<String>> entry : entries) {

			ShopModelBean shopBean = new ShopModelBean();
			List<String> list = entry.getValue();

			shopBean.setShopNo(list.get(0).toString());
			listShop.add(shopBean);
		}
		return listShop;
	}

	private List<ShopModelBean> checkShopHaveMaster(List<ShopModelBean> shopInFileList){
		// It should not be like this due to performance concern. Need to modify the source code and logic.
		// I will comment them out first and simply return the shops in the file.
		
		/*
		 * ServiceResult<List<ShopModelBean>> shopInMasterListResult =
		 * promotionService.getShopMasterList(new ShopModelBean()); List<ShopModelBean>
		 * shopInMasterList = new ArrayList<ShopModelBean>();
		 * 
		 * 
		 * if(shopInMasterListResult.isSuccess()) { shopInMasterList =
		 * shopInMasterListResult.getResult(); }
		 * 
		 * 
		 * List<ShopModelBean> shopInFileAndInMasterList = new ArrayList<ShopModelBean>();
		 * shopInFileAndInMasterList = compareShopLists(shopInMasterList,
		 * shopInFileList);
		 * 
		 * return shopInFileAndInMasterList;
		 */
		return shopInFileList;
	}

	private static List<ShopModelBean> compareShopLists(List<ShopModelBean> shopInMasterList,
			List<ShopModelBean> shopInFileList) {
		List<ShopModelBean> shopInFileAndInMasterList = new ArrayList<ShopModelBean>();
		shopInMasterList.forEach(
				p -> shopInFileList.stream().filter(p1 -> p.getShopNo().equals(p1.getShopNo())).forEach(shopInFileAndInMasterList::add));
		return shopInFileAndInMasterList;

	}

	private List<PromotionShopModelBean> findShopId(List<ShopModelBean> shopInFileAndInMasterList, String promotionId,
			String userId) {
		List<PromotionShopModelBean> list = new ArrayList<PromotionShopModelBean>();

		try {

			for (ShopModelBean shopData : shopInFileAndInMasterList) {
				ServiceResult<List<ShopModelBean>> shopDataDetailResult = promotionService
						.getShopMasterList(shopData);
				if (shopDataDetailResult.isSuccess()) {
					PromotionShopModelBean promotionShopModelBean = new PromotionShopModelBean();
					
					promotionShopModelBean.setShopId(shopDataDetailResult.getResult().get(0).getShopId().toString());
					promotionShopModelBean.setShopTypeId(shopDataDetailResult.getResult().get(0).getShopTypeId().toString());
					promotionShopModelBean.setLocationId(shopDataDetailResult.getResult().get(0).getLocationId());
					
					list.add(promotionShopModelBean);
				}
			}

			list.forEach(f -> f.setCreatedBy(userId));
			list.forEach(f -> f.setPromotionId(Long.valueOf(promotionId)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/* ################################ Attribute ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Attribute List")
	@PostMapping(value = "/getPromotionAttributeList", produces = "application/json")
	public ApiPageResponse<List<PromotionAttributeModelBean>> getPromotionAttributeList(
			@RequestBody ApiPageRequest<PromotionAttributeModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PromotionAttributeModelBean>> serviceResult = promotionService.getPromotionAttributeList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Program Attribute")
	@PostMapping(value = "/savePromotionAttribute", produces = "application/json")
	public ApiResponse<PromotionAttributeModelBean> savePromotionAttribute(@RequestBody ApiRequest<PromotionAttributeModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<PromotionAttributeModelBean> serviceResult;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		if (request.getData().getCreatedDate() != null) {
			serviceResult = promotionService.updatePromotionAttribute(request.getData());
		} else {
			serviceResult = promotionService.createPromotionAttribute(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("500", "No record is updated or inserted.");
	}

	@WritePermission
	@ApiOperation(value = "Delete Program Attribute")
	@PostMapping(value = "/deletePromotionAttribute", produces = "application/json")
	public ApiResponse<Integer> deletePromotionAttribute(@RequestBody ApiRequest<PromotionAttributeModelBean> request) {

		ServiceResult<Integer> serviceResult = promotionService.deletePromotionAttribute(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("The promotion attribute (" + request.getData().getPromotionAttrId() + ") cannot be deleted.");
	}

}
