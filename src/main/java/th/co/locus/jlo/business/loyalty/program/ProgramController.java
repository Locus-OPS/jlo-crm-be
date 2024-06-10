package th.co.locus.jlo.business.loyalty.program;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.program.bean.CardTierModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.LocationBasePointModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.PrivilegeModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramAttributeModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramPointTypeModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramShopModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramTierModelBean;
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
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

@Api(value = "API for Loyalty Program Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/loyalty/program")
public class ProgramController extends BaseController {

	@Value("${database.schema}")
	private String databaseSchema;
	
	@Autowired
	private ProgramService programService;
	
	/*################################ Program ###############################*/	
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program List")
	@PostMapping(value = "/getProgramList", produces = "application/json")
	public ApiPageResponse<List<ProgramModelBean>> getProgramList(@RequestBody ApiPageRequest<ProgramCriteriaModelBean> request) {
		ProgramCriteriaModelBean programCriteriaModelBean = request.getData();
		StringUtil.nullifyObject(programCriteriaModelBean);
		programCriteriaModelBean.setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProgramModelBean>> serviceResult = programService.getProgramList(programCriteriaModelBean, pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Program")
	@PostMapping(value = "/saveProgram", produces = "application/json")
	public ApiResponse<ProgramModelBean> saveProgram(@RequestBody ApiRequest<ProgramModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<ProgramModelBean> serviceResult;
		ProgramModelBean bean = request.getData();
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());
		bean.setBuId(getBuId());
		
		LocalDate startDate = bean.getStartDate();
		String strStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		LocalDate endDate = bean.getEndDate();
		String strEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		bean.setStrStartDate(strStartDate);
		bean.setStrEndDate(strEndDate);
		
		if (request.getData().getProgramId() != null) {
			serviceResult = programService.updateProgram(bean);
		} else {
			serviceResult = programService.createProgram(bean);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete Loyalty Program")
	@PostMapping(value = "/deleteProgram", produces = "application/json")
	public ApiResponse<Integer> deleteProgram(@RequestBody ApiRequest<ProgramModelBean> request) {
		return ApiResponse.success(programService.deleteProgram(request.getData()).getResult());
	}
	
	/*################################ Program Attribute Group ###############################*/	
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Attribute Group List")
	@PostMapping(value = "/getAttributeGroupList", produces = "application/json")
	public ApiResponse<List<ProgramAttributeModelBean>> getAttributeGroupList(@RequestBody ApiRequest<String> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<List<ProgramAttributeModelBean>> serviceResult = programService.getAttributeGroupList(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	/*################################ Program	Attribute ###############################*/
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Attribute List")
	@PostMapping(value = "/getAttributeList", produces = "application/json")
	public ApiPageResponse<List<ProgramAttributeModelBean>> getAttributeList(@RequestBody ApiPageRequest<ProgramAttributeModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProgramAttributeModelBean>> serviceResult = programService.getAttributeList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Program Attribute")
	@PostMapping(value = "/saveAttribute", produces = "application/json")
	public ApiResponse<ProgramAttributeModelBean> saveAttribute(@RequestBody ApiRequest<ProgramAttributeModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());

		ServiceResult<ProgramAttributeModelBean> serviceResult;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		// check by attrId
		if (request.getData().getAttrId() != null) {
			serviceResult = programService.updateAttribute(request.getData());
		} else {
			serviceResult = programService.createAttribute(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@ApiOperation(value = "Delete Loyalty Program Attribute")
	@PostMapping(value = "/deleteAttribute", produces = "application/json")
	public ApiResponse<Integer> deleteAttribute(@RequestBody ApiRequest<ProgramAttributeModelBean> request) {
		return ApiResponse.success(programService.deleteAttribute(request.getData()).getResult());
	}

	/*
	 * ################################ Program Tier ###############################
	 */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Tier List")
	@PostMapping(value = "/getTierList", produces = "application/json")
	public ApiPageResponse<List<ProgramTierModelBean>> getTierList(@RequestBody ApiPageRequest<ProgramTierModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProgramTierModelBean>> serviceResult = programService.getTierList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ReadPermission
	@ApiOperation(value = "Get Primary Loyalty Program Tier")
	@PostMapping(value = "/getPrimaryTier", produces = "application/json")
	public ApiResponse<ProgramTierModelBean> getPrimaryTier(@RequestBody ApiRequest<ProgramTierModelBean> request) {

		ProgramTierModelBean criteria = request.getData();
		StringUtil.nullifyObject(criteria);
		criteria.setBaseFlag("Y");
		criteria.setBuId(getBuId());

		ServiceResult<ProgramTierModelBean> serviceResult = programService.getPrimaryTier(criteria);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Program Tier")
	@PostMapping(value = "/saveTier", produces = "application/json")
	public ApiResponse<ProgramTierModelBean> saveTier(@RequestBody ApiRequest<ProgramTierModelBean> request) {

		ProgramTierModelBean programTierModelBean = request.getData();
		StringUtil.nullifyObject(programTierModelBean);

		ServiceResult<ProgramTierModelBean> serviceResult;
		programTierModelBean.setCreatedBy(getUserId());
		programTierModelBean.setUpdatedBy(getUserId());
		programTierModelBean.setBuId(getBuId());
		
		Long removePrimaryTierId = programTierModelBean.getRemovePrimaryTierId();
		if (removePrimaryTierId != null) {
			ServiceResult<ProgramTierModelBean> removePrimaryTierResult = programService.removePrimaryFlagTier(programTierModelBean);
			if (!removePrimaryTierResult.isSuccess()) {
				return ApiResponse.fail("Cannot remove primary flag.");
			}
		}
		
		if (programTierModelBean.getTierId() != null) {
			serviceResult = programService.updateTier(programTierModelBean);
		} else {
			serviceResult = programService.createTier(programTierModelBean);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@ApiOperation(value = "Delete Loyalty Program Tier")
	@PostMapping(value = "/deleteTier", produces = "application/json")
	public ApiResponse<Integer> deleteTier(@RequestBody ApiRequest<ProgramTierModelBean> request) {
		return ApiResponse.success(programService.deleteTier(request.getData()).getResult());
	}
	
	/*################################ Location Base Point ###############################*/	
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Location Base Point List")
	@PostMapping(value = "/getLocationBasePointList", produces = "application/json")
	public ApiPageResponse<List<LocationBasePointModelBean>> getLocationBasePointList(@RequestBody ApiPageRequest<LocationBasePointModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<LocationBasePointModelBean>> serviceResult = programService.getLocationBasePointList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Not Selected Location Base Point List")
	@PostMapping(value = "/getNotSelectedLocationBasePointList", produces = "application/json")
	public ApiPageResponse<List<LocationBasePointModelBean>> getNotSelectedLocationBasePointList(@RequestBody ApiPageRequest<LocationBasePointModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<LocationBasePointModelBean>> serviceResult = programService.getNotSelectedLocationBasePointList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create Loyalty Location Base Point")
	@PostMapping(value = "/createLocationBasePoint", produces = "application/json")
	public ApiResponse<Integer> createLocationBasePoint(@RequestBody ApiRequest<LocationBasePointModelBean[]> request) {

		LocationBasePointModelBean[] locationBasePoints = request.getData();
		for (LocationBasePointModelBean locationBasePoint : locationBasePoints) {
			locationBasePoint.setCreatedBy(getUserId());
			locationBasePoint.setUpdatedBy(getUserId());
			StringUtil.nullifyObject(locationBasePoint);
		}
		ServiceResult<Integer> serviceResult = programService.createLocationBasePoint(request.getData());

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Update Loyalty Location Base Point")
	@PostMapping(value = "/updateLocationBasePoint", produces = "application/json")
	public ApiResponse<LocationBasePointModelBean> updateLocationBasePoint(@RequestBody ApiRequest<LocationBasePointModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<LocationBasePointModelBean> serviceResult;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		serviceResult = programService.updateLocationBasePoint(request.getData());
		
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Delete Loyalty Location Base Point")
	@PostMapping(value = "/deleteLocationBasePoint", produces = "application/json")
	public ApiResponse<Integer> deleteLocationBasePoint(@RequestBody ApiRequest<LocationBasePointModelBean> request) {
		return ApiResponse.success(programService.deleteLocationBasePoint(request.getData()).getResult());
	}

	/* ################################ Card Tier ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Card Tier List")
	@PostMapping(value = "/getCardTierList", produces = "application/json")
	public ApiPageResponse<List<CardTierModelBean>> getCardTierList(
			@RequestBody ApiPageRequest<CardTierModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CardTierModelBean>> serviceResult = programService.getCardTierList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Card Tier List")
	@PostMapping(value = "/getCardTierListWithoutPaging", produces = "application/json")
	public ApiResponse<List<CardTierModelBean>> getCardTierListWithoutPaging(
			@RequestBody ApiRequest<CardTierModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<List<CardTierModelBean>> serviceResult = programService
				.getCardTierListWithoutPaging(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@ReadPermission
	@ApiOperation(value = "Get Primary Loyalty Card Tier")
	@PostMapping(value = "/getPrimaryCardTier", produces = "application/json")
	public ApiResponse<CardTierModelBean> getPrimaryCardTier(@RequestBody ApiRequest<CardTierModelBean> request) {

		CardTierModelBean criteria = request.getData();
		StringUtil.nullifyObject(criteria);
		criteria.setPrimaryFlag("Y");

		ServiceResult<CardTierModelBean> serviceResult = programService.getPrimaryCardTier(criteria);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty Card Tier")
	@PostMapping(value = "/saveCardTier", produces = "application/json")
	public ApiResponse<CardTierModelBean> saveCardTier(@RequestBody ApiRequest<CardTierModelBean> request) {
		CardTierModelBean cardTierModelBean = request.getData();
		StringUtil.nullifyObject(cardTierModelBean);

		cardTierModelBean.setCreatedBy(getUserId());
		cardTierModelBean.setUpdatedBy(getUserId());

		Long removePrimaryCardTierId = cardTierModelBean.getRemovePrimaryCardTierId();
		if (removePrimaryCardTierId != null) {
			ServiceResult<CardTierModelBean> removePrimaryTierResult = programService
					.removePrimaryFlagCardTier(cardTierModelBean);
			if (!removePrimaryTierResult.isSuccess()) {
				return ApiResponse.fail("Cannot remove primary flag.");
			}
		}

		ServiceResult<CardTierModelBean> serviceResult;
		if (request.getData().getTierCardTypeId() != null) {
			serviceResult = programService.updateCardTier(request.getData());
		} else {
			serviceResult = programService.createCardTier(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete Loyalty Card Tier")
	@PostMapping(value = "/deleteCardTier", produces = "application/json")
	public ApiResponse<Integer> deleteCardTier(@RequestBody ApiRequest<CardTierModelBean> request) {
		return ApiResponse.success(programService.deleteCardTier(request.getData()).getResult());
	}
	
	/*################################ Privilege ###############################*/	
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Privilege List")
	@PostMapping(value = "/getPrivilegeList", produces = "application/json")
	public ApiPageResponse<List<PrivilegeModelBean>> getPrivilegeList(@RequestBody ApiPageRequest<PrivilegeModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PrivilegeModelBean>> serviceResult = programService.getPrivilegeList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Privilege")
	@PostMapping(value = "/savePrivilege", produces = "application/json")
	public ApiResponse<PrivilegeModelBean> savePrivilege(@RequestBody ApiRequest<PrivilegeModelBean> request) {

		StringUtil.nullifyObject(request.getData());

		ServiceResult<PrivilegeModelBean> serviceResult;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getServiceId() != null) {
			serviceResult = programService.updatePrivilege(request.getData());
		} else {
			serviceResult = programService.createPrivilege(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete Privilege")
	@PostMapping(value = "/deletePrivilege", produces = "application/json")
	public ApiResponse<Integer> deletePrivilege(@RequestBody ApiRequest<PrivilegeModelBean> request) {
		return ApiResponse.success(programService.deletePrivilege(request.getData()).getResult());
	}
	
	/*################################ Point Type ###############################*/	
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Point Type List")
	@PostMapping(value = "/getPointTypeList", produces = "application/json")
	public ApiPageResponse<List<ProgramPointTypeModelBean>> getPointTypeList(@RequestBody ApiPageRequest<ProgramPointTypeModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProgramPointTypeModelBean>> serviceResult = programService.getPointTypeList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ReadPermission
	@ApiOperation(value = "Get Primary Point Type")
	@PostMapping(value = "/getPrimaryPointType", produces = "application/json")
	public ApiResponse<ProgramPointTypeModelBean> getPrimaryPointType(@RequestBody ApiRequest<ProgramPointTypeModelBean> request) {

		ProgramPointTypeModelBean criteria = request.getData();
		StringUtil.nullifyObject(criteria);
		criteria.setPrimaryYn("Y");

		ServiceResult<ProgramPointTypeModelBean> serviceResult = programService.getPrimaryPointType(criteria);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update Program PointType")
	@PostMapping(value = "/savePointType", produces = "application/json")
	public ApiResponse<ProgramPointTypeModelBean> savePointType(
			@RequestBody ApiRequest<ProgramPointTypeModelBean> request) {
		ProgramPointTypeModelBean programPointTypeModelBean = request.getData();
		StringUtil.nullifyObject(programPointTypeModelBean);

		ServiceResult<ProgramPointTypeModelBean> serviceResult;
		programPointTypeModelBean.setCreatedBy(getUserId());
		programPointTypeModelBean.setUpdatedBy(getUserId());

		Long removePrimaryPointTypeId = programPointTypeModelBean.getRemovePrimaryPointTypeId();
		if (removePrimaryPointTypeId != null) {
			ServiceResult<ProgramPointTypeModelBean> removePrimaryPointTypeResult = programService
					.removePrimaryFlagPointType(programPointTypeModelBean);
			if (!removePrimaryPointTypeResult.isSuccess()) {
				return ApiResponse.fail("Cannot remove primary flag.");
			}
		}

		if (programPointTypeModelBean.getPointTypeId() != null) {
			serviceResult = programService.updatePointType(request.getData());
		} else {
			serviceResult = programService.createPointType(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@ApiOperation(value = "Delete Program PointType")
	@PostMapping(value = "/deletePointType", produces = "application/json")
	public ApiResponse<Integer> deletePointType(@RequestBody ApiRequest<ProgramPointTypeModelBean> request) {
		return ApiResponse.success(programService.deletePointType(request.getData()).getResult());
	}
	
	/* ################################ Program ############################### */

	@ReadPermission
	@ApiOperation(value = "Get Loyalty Program Program List")
	@PostMapping(value = "/getShopList", produces = "application/json")
	public ApiPageResponse<List<ProgramShopModelBean>> getShopList(
			@RequestBody ApiPageRequest<ProgramShopModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProgramShopModelBean>> serviceResult = programService.getShopList(request.getData(),
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
		ServiceResult<Page<ShopModelBean>> serviceResult = programService.getShopMasterList(request.getData(),
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
	@PostMapping(value = "/insertProgramShop", produces = "application/json")
	public ApiResponse<Integer> insertProgramShop(@RequestBody ApiRequest<ProgramShopModelBean[]> request) {

		StringUtil.nullifyObject(request.getData());

		List<ProgramShopModelBean> programShoplList = new ArrayList<>(Arrays.asList(request.getData()));
		programShoplList.stream().forEach(item -> item.setCreatedBy(getUserId()));
		programShoplList.stream().forEach(item -> item.setUpdatedBy(getUserId()));

		ServiceResult<Integer> serviceResult = programService
				.insertProgramShop(programShoplList.toArray(new ProgramShopModelBean[programShoplList.size()]));

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}

	}

	@WritePermission
	@ApiOperation(value = "Delete Program Program")
	@PostMapping(value = "/deleteShop", produces = "application/json")
	public ApiResponse<Integer> deleteShop(@RequestBody ApiRequest<ProgramShopModelBean> request) {

		ServiceResult<Integer> serviceResult = programService.deleteShop(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail("The Program (" + request.getData().getShopId() + ") cannot be deleted.");
	}

	@WritePermission
	@ApiOperation(value = "Upload Program Program")
	@PostMapping(value = "/uploadProgramShopExcel", produces = "application/json")
	public ApiResponse<HashMap<String, Integer>> uploadProgramShopExcel(@RequestBody HashMap<String, String> request)
			throws Exception {

		/*
		 * It is strange that the logic does not check the duplicate in the selected
		 * shop list. Need to check with BA and other developers.
		 */

		InputStream is = null;
		String programId = request.get("programId");

		// 1. Read file
		is = FileUtil.readInputStream(request.get("base64File"));
		Map<Integer, List<String>> sheet = FileUtil.readExcelDataByFilePath(is);

		// 2. Get data in file
		List<Entry<Integer, List<String>>> entries = CommonUtil.convertMapToList(sheet);
		List<ShopModelBean> shopInFileList = convertShopModelBeanToList(entries);

		// 3. Check data have in master
		List<ShopModelBean> shopInFileAndInMasterList = new ArrayList<ShopModelBean>();
		shopInFileAndInMasterList = checkShopHaveMaster(shopInFileList);

		// 4. find item code
		// This is important. It will convert ShopModalModelBean -> ShopModelBean to be inserted under the program.
		List<ProgramShopModelBean> confirmedProgramShopList = findShopId(shopInFileAndInMasterList, programId, getUserId());

		// 5. insert program shop
		programService.insertProgramShop(confirmedProgramShopList.stream().toArray(ProgramShopModelBean[]::new));

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
		 * programService.getShopMasterList(new ShopModelBean()); List<ShopModelBean>
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

	@SuppressWarnings("unused")
	private static List<ShopModelBean> compareShopLists(List<ShopModelBean> shopInMasterList,
			List<ShopModelBean> shopInFileList) {
		List<ShopModelBean> shopInFileAndInMasterList = new ArrayList<ShopModelBean>();
		shopInMasterList.forEach(
				p -> shopInFileList.stream().filter(p1 -> p.getShopNo().equals(p1.getShopNo())).forEach(shopInFileAndInMasterList::add));
		return shopInFileAndInMasterList;

	}

	private List<ProgramShopModelBean> findShopId(List<ShopModelBean> shopInFileAndInMasterList, String programId,
			String userId) {
		List<ProgramShopModelBean> list = new ArrayList<ProgramShopModelBean>();

		try {

			for (ShopModelBean shopData : shopInFileAndInMasterList) {
				ServiceResult<List<ShopModelBean>> shopDataDetailResult = programService
						.getShopMasterList(shopData);
				if (shopDataDetailResult.isSuccess()) {
					ProgramShopModelBean programShopModelBean = new ProgramShopModelBean();
					
					programShopModelBean.setShopId(shopDataDetailResult.getResult().get(0).getShopId().toString());
					programShopModelBean.setShopTypeId(shopDataDetailResult.getResult().get(0).getShopTypeId().toString());
					programShopModelBean.setLocationId(shopDataDetailResult.getResult().get(0).getLocationId());
					
					list.add(programShopModelBean);
				}
			}

			list.forEach(f -> f.setCreatedBy(userId));
			list.forEach(f -> f.setProgramId(Long.valueOf(programId)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@ReadPermission
	@ApiOperation(value = "Get Table List")
	@PostMapping(value = "/getTableList", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getTableList(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = programService.getTableList(databaseSchema,
				request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@ReadPermission
	@ApiOperation(value = "Get a column List in a specific table.")
	@PostMapping(value = "/getColumnList", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getColumnList(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = programService.getColumnList(databaseSchema,
				request.getData());
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}
}
