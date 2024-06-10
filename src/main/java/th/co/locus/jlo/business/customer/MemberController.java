package th.co.locus.jlo.business.customer;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.MemberAttachmentData;
import th.co.locus.jlo.business.customer.bean.MemberCardData;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.customer.bean.MemberPointData;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchTransactionCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
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
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;
import th.co.locus.jlo.util.file.FileService;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

@Slf4j
@Api(value = "API for Member Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/member")
public class MemberController extends BaseController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${attachment.path.att}")
	private String attPath;
	//=========================================================================================================
	//===================================================== Member ============================================
	//=========================================================================================================
	@WritePermission
	@ApiOperation(value = "Update Member")
	@PostMapping(value = "/saveMember", produces = "application/json")
	public ApiResponse<MemberData> saveMember(@RequestBody ApiRequest<MemberData> request){
		
		MemberData mData = request.getData();
		StringUtil.nullifyObject(mData);
		mData.setUpdatedBy(getUserId());
		ServiceResult<MemberData> serviceResult = memberService.updateMember(mData);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Member By Id")
	@PostMapping(value = "/getMemberById", produces = "application/json")
	public ApiResponse<MemberData> getMemberById(@RequestBody ApiPageRequest<MemberData> request) {
		log.info("MemberCriteria : " + request.getData());
		MemberData cusData = request.getData();
		cusData.setBuId(getBuId());
		ServiceResult<MemberData> serviceResult = memberService.getMemberById(cusData);
		if (serviceResult.isSuccess()) {
			MemberData member = serviceResult.getResult();
			MemberCardData mCard = new MemberCardData();
			mCard.setMemberId(member.getMemberId());
			mCard.setPrimaryYn("Y");
			ServiceResult<MemberCardData> serviceResultMemberCard = memberService.getMemberCardById(mCard);
			if(serviceResultMemberCard.isSuccess()) {
				member.setMemberCardNoData(serviceResultMemberCard.getResult());
			}
			MemberPointData mPoint = new MemberPointData();
			mPoint.setMemberId(member.getMemberId());
			mPoint.setProgramId(member.getProgramId());
			ServiceResult<MemberPointData> serviceResultMemberPoint = memberService.getMemberPoint(mPoint);
			if (serviceResultMemberPoint.isSuccess()) {
				member.setMemberPointData(serviceResultMemberPoint.getResult());
			}
			log.info("MemberData : " + member);

			return ApiResponse.success(member);
		} else {
			return ApiResponse.fail();
		}
	}

	//=========================================================================================================
	//===================================================== Address ===========================================
	//=========================================================================================================
	@ApiOperation(value = "Get Member Address List")
	@PostMapping(value = "/getMemberAddressList", produces = "application/json")
	public ApiPageResponse<List<AddressData>> getMemberAddressList(@RequestBody ApiPageRequest<AddressData> request) {
		request.getData().setBuId(getBuId());
		log.info("MemberId : "+request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<AddressData>> serviceResult = memberService.getMemberAddressList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Member Address")
	@PostMapping(value = "/saveMemberAddress", produces = "application/json")
	public ApiResponse<AddressData> saveMemberAddress(@RequestBody ApiRequest<AddressData> request){
		ServiceResult<AddressData> serviceResult;
		if(request.getData().getAddressId()!=null) {
			AddressData addrData = request.getData();
			addrData.setUpdatedBy(getUserId());
			serviceResult = memberService.updateMemberAddress(addrData);
		}else {
			AddressData addrData = request.getData();
			addrData.setCreatedBy(getUserId());
			addrData.setUpdatedBy(getUserId());
			addrData.setBuId(getBuId());
			serviceResult = memberService.createMemberAddress(addrData);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete Member Address")
	@PostMapping(value = "/deleteMemberAddress", produces = "application/json")
	public ApiResponse<Integer> deleteMemberAddress(@RequestBody ApiRequest<AddressData> request) {
		return ApiResponse.success(memberService.deleteMemberAddress(request.getData()).getResult());
	}
	
	
	@ReadPermission
	@ApiOperation(value = "Get Member Address By Primary")
	@PostMapping(value = "/getMemberAddressPrimary", produces = "application/json")
	public ApiResponse<AddressData> getCustomerAddressPrimary(@RequestBody ApiPageRequest<AddressData> request) {
		log.info("CustomerListCriteria : "+request.getData());
		AddressData addressCri = request.getData();
		StringUtil.nullifyObject(addressCri);
		addressCri.setPrimaryYn("Y");
		ServiceResult<AddressData> serviceResult = memberService.getMemberAddressByPrimary(addressCri);
		if (serviceResult.isSuccess()) {
			AddressData addressData = serviceResult.getResult();
			log.info("MemberAddressData : "+addressData);

			return ApiResponse.success(addressData);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	//=========================================================================================================
	//===================================================== Card ==============================================
	//=========================================================================================================
	@ReadPermission
	@ApiOperation(value = "Get Member Card List")
	@PostMapping(value = "/getMemberCardList", produces = "application/json")
	public ApiPageResponse<List<MemberCardData>> getMemberCardList(@RequestBody ApiPageRequest<MemberCardData> request) {
		log.info("MemberId : "+request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<MemberCardData>> serviceResult = memberService.getMemberCardList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}

	//=========================================================================================================
	//===================================================== Attachment ========================================
	//=========================================================================================================
	@ReadPermission
	@ApiOperation(value = "Get Member Attachment List")
	@PostMapping(value = "/getMemberAttachmentList", produces = "application/json")
	public ApiPageResponse<List<MemberAttachmentData>> getMemberAttachmentList(@RequestBody ApiPageRequest<MemberAttachmentData> request) {
		log.info("MemberId : "+request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<MemberAttachmentData>> serviceResult = memberService.getMemberAttachmentList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create Member Attachment")
	@PostMapping(value = "/createAttachment")
	public ApiResponse<MemberAttachmentData> createAttachment(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "memberId", required = false) Integer memberId,
			@RequestParam(value = "memberAttId", required = false) Long memberAttId,
			@RequestParam(value = "attId", required = false) Long attId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "descp", required = false) String descp) {
		
		MemberAttachmentData memberAtt = new MemberAttachmentData();
		memberAtt.setMemberId(memberId);
		memberAtt.setMemberAttId(memberAttId);
		memberAtt.setAttId(attId);
		memberAtt.setTitle(title);
		memberAtt.setDescp(descp);
		memberAtt.setBuId(getBuId());
		memberAtt.setCreatedBy(getUserId());
		memberAtt.setUpdatedBy(getUserId());
		

		FileModelBean fileBean = null;
		if (file != null) {
			fileBean = new FileModelBean();
			fileBean.setFilePath(attPath + File.separator + "member"+File.separator+memberId);
			fileBean.setFileExtension(CommonUtil.getFileExtension(file));
			fileBean.setFileName(file.getOriginalFilename());
			fileBean.setFileSize(file.getSize());
			fileBean.setCreatedBy(getUserId());
			fileBean.setUpdatedBy(getUserId());
		}
		try {
			ServiceResult<MemberAttachmentData> serviceResult = null;
			if (StringUtils.isEmpty(memberAttId)) {
				serviceResult = memberService.createAttachment(memberAtt, fileBean, file);
			} else {
	
				serviceResult = memberService.updateAttachment(memberAtt, fileBean, file);
			}
	
			if (serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
		}catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return ApiResponse.fail();

	}
	
	@WritePermission
	@ApiOperation(value = "Delete Member Attachment")
	@PostMapping(value = "/deleteMemberAttachment", produces = "application/json")
	public ApiResponse<Boolean> deleteMemberAttachment(@RequestBody ApiPageRequest<MemberAttachmentData> request) {	
		MemberAttachmentData mData = request.getData();
		mData.setUpdatedBy(getUserId());
		mData.setBuId(getBuId());
		log.debug(mData.toString());
		ServiceResult<Boolean> serviceResult = memberService.deleteMemberAttachment(mData);	
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Member Document")
	@GetMapping(value = "/doc")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(@RequestParam("filePath") String filePath) {
		Resource file = fileService.loadFile(filePath);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	//=========================================================================================================
	//===================================================== Case ==============================================
	//=========================================================================================================
	@ReadPermission
	@ApiOperation(value = "Get Member Case List")
	@PostMapping(value = "/getMemberCaseList", produces = "application/json")
	public ApiPageResponse<List<CaseModelBean>> getMemberCaseList(@RequestBody ApiPageRequest<CaseModelBean> request) {
		log.info("CaseModelBean : "+request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CaseModelBean>> serviceResult = memberService.getMemberCaseList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	//=========================================================================================================
	//===================================================== Transaction ==============================================
	//=========================================================================================================
	@ReadPermission
	@ApiOperation(value = "Get Member Transaction List")
	@PostMapping(value = "/getMemberTransactionList", produces = "application/json")
	public ApiPageResponse<List<TransactionModelBean>> getMemberTransactionList(@RequestBody ApiPageRequest<SearchTransactionCriteriaModelBean> request) {
		log.info("SearchTransactionCriteriaModelBean : "+request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<TransactionModelBean>> serviceResult = memberService.getMemberTransactionList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Block Card")
	@PostMapping(value = "/saveBlockCard", produces = "application/json")
	public ApiResponse<MemberCardData> saveBlockCard(@RequestBody ApiRequest<MemberCardData> request){
		
		MemberCardData mData = request.getData();
		mData.setUpdatedBy(getUserId());
		ServiceResult<MemberCardData> serviceResult = memberService.saveBlockCard(mData);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Member Tier List")
	@PostMapping(value = "/getMemberTierList", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getMemberTierList(@RequestBody ApiRequest<MemberCardData> request) {
		MemberCardData mData = request.getData();
		ServiceResult<List<SelectorModelBean>> serviceResult = memberService.getMemberTierList(mData);
		if (serviceResult.isSuccess()) {
			List<SelectorModelBean> tierList = serviceResult.getResult();
			log.info("List<SelectorModelBean> : "+tierList);

			return ApiResponse.success(tierList);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Re-Issues Card")
	@PostMapping(value = "/saveReIssuesCard", produces = "application/json")
	public ApiResponse<MemberCardData> saveReIssuesCard(@RequestBody ApiRequest<MemberCardData> request){
		
		MemberCardData mData = request.getData();
		mData.setUpdatedBy(getUserId());
		ServiceResult<MemberCardData> serviceResult = memberService.saveReIssuesCard(mData);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@ReadPermission
	@ApiOperation(value = "Get Member point")
	@PostMapping(value = "/getMemberPoint", produces = "application/json")
	public ApiResponse<MemberPointData> getMemberPoint(@RequestBody ApiPageRequest<MemberPointData> request) {
		MemberPointData mPoint = request.getData();
		ServiceResult<MemberPointData> serviceResult = memberService.getMemberPoint(mPoint);
		if (serviceResult.isSuccess()) {
			MemberPointData memberPoint = serviceResult.getResult();
			log.info("memberPoint : " + memberPoint);

			return ApiResponse.success(memberPoint);
		} else {
			return ApiResponse.fail();
		}
	}

}
