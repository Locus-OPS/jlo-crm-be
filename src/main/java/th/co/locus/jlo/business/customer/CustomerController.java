package th.co.locus.jlo.business.customer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.ContactData;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.CustomerListCriteria;
import th.co.locus.jlo.business.customer.bean.CustomerVerifyData;
import th.co.locus.jlo.business.sr.bean.ServiceRequestModelBean;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.file.FileService;

@Slf4j
@RestController
@RequestMapping("api/customer")
public class CustomerController extends BaseController {
	
	@Autowired
	private CustomerService customerService;
	
	@Value("${attachment.path.customer.profile_image}")
	private String profileImagePath;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * Receives a customer list.
	 * Adds logs to track from sometime cannot return a result.
	 * @param request the criteria to search
	 * @return the customer list
	 */
	@PostMapping(value = "/getCustomerList", produces = "application/json")
	public ApiPageResponse<List<CustomerData>> getCustomerList(
			@RequestBody ApiPageRequest<CustomerListCriteria> request) {
		log.info("getCustomerList CustomerListCriteria : " + request.getData());

		PageRequest pageRequest = getPageRequest(request);
		CustomerListCriteria criteria = request.getData();
		CommonUtil.nullifyObject(criteria);
		criteria.setBuId(getBuId());
		log.info("criteria : " + criteria);
		ServiceResult<Page<CustomerData>> serviceResult = customerService.getCustomerList(criteria, pageRequest);
		log.info("serviceResult : " + serviceResult.toString());
		if (serviceResult.isSuccess()) {
			List<CustomerData> customerList = serviceResult.getResult().getContent();
			log.info("TotalCustomerList : " + (customerList != null ? customerList.size() : 0));
			log.info("customerList : " + customerList);
			
			return ApiPageResponse.success(customerList,
					serviceResult.getResult().getTotalElements());
		} else {
			log.info("Return fail");
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@PostMapping(value = "/saveCustomer", produces = "application/json")
	public ApiResponse<CustomerData> saveCustomer(@RequestBody ApiRequest<CustomerData> request) {
		CommonUtil.nullifyObject(request.getData());
		ServiceResult<CustomerData> serviceResult;
		if (request.getData().getCustomerId() != null) {
			CustomerData cusData = request.getData();
			cusData.setUpdatedBy(getUserId());
			serviceResult = customerService.updateCustomer(cusData);
		} else {
			CustomerData cusData = request.getData();
			cusData.setCreatedBy(getUserId());
			cusData.setUpdatedBy(getUserId());
			cusData.setBuId(getBuId());
			serviceResult = customerService.createCustomer(cusData);
		}
		if (serviceResult.isSuccess()) {
			// Create Contact
			ContactData contact = new ContactData();
			contact.setCreatedBy(getUserId());
			contact.setUpdatedBy(getUserId());
			contact.setCustomerId(serviceResult.getResult().getCustomerId());
			customerService.createContact(contact);
			
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@ReadPermission
	@PostMapping(value = "/getCustomerAddressList", produces = "application/json")
	public ApiPageResponse<List<AddressData>> getCustomerAddressList(@RequestBody ApiPageRequest<AddressData> request) {
		log.info("CustomerId : "+request.getData());
		
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<AddressData>> serviceResult = customerService.getCustomerAddressList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@PostMapping(value = "/saveCustomerAddress", produces = "application/json")
	public ApiResponse<AddressData> saveCustomerAddress(@RequestBody ApiRequest<AddressData> request){
		ServiceResult<AddressData> serviceResult;
		if(request.getData().getAddressId()!=null) {
			AddressData addrData = request.getData();
			addrData.setUpdatedBy(getUserId());
			serviceResult = customerService.updateCustomerAddress(addrData);
		}else {
			AddressData addrData = request.getData();
			addrData.setCreatedBy(getUserId());
			addrData.setUpdatedBy(getUserId());
			addrData.setBuId(getBuId());
			serviceResult = customerService.createCustomerAddress(addrData);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/deleteCustomerAddress", produces = "application/json")
	public ApiResponse<Integer> deleteCustomerAddress(@RequestBody ApiRequest<AddressData> request) {
		return ApiResponse.success(customerService.deleteCustomerAddress(request.getData()).getResult());
	}
	
	@ReadPermission
	@PostMapping(value = "/getCustomerAddressPrimary", produces = "application/json")
	public ApiResponse<AddressData> getCustomerAddressPrimary(@RequestBody ApiPageRequest<AddressData> request) {
		log.info("CustomerListCriteria : "+request.getData());
		AddressData addressCri = request.getData();
		CommonUtil.nullifyObject(addressCri);
		addressCri.setPrimaryYn("Y");
		addressCri.setBuId(getBuId());
		ServiceResult<AddressData> serviceResult = customerService.getCustomerAddressByPrimary(addressCri);
		if (serviceResult.isSuccess()) {
			AddressData addressData = serviceResult.getResult();
			log.info("CustomerAddressData : "+addressData);

			return ApiResponse.success(addressData);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ReadPermission
	@PostMapping(value = "/getCustomerById", produces = "application/json")
	public ApiResponse<CustomerData> getCustomerById(@RequestBody ApiPageRequest<CustomerData> request) {
		log.info("CustomerListCriteria : "+request.getData());
		CustomerData cusData = request.getData();
		cusData.setBuId(getBuId());
		ServiceResult<CustomerData> serviceResult = customerService.getCustomerById(cusData);
		if (serviceResult.isSuccess()) {
			CustomerData customerList = serviceResult.getResult();
			log.info("CustomerData : "+customerList);

			return ApiResponse.success(customerList);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	@WritePermission
	@PostMapping(value = "/updateCustomerStatus", produces = "application/json")
	public ApiResponse<CustomerData> updateCustomerStatus(@RequestBody ApiRequest<CustomerData> request){
		ServiceResult<CustomerData> serviceResult;
		CustomerData cusData = request.getData();
		cusData.setUpdatedBy(getUserId());
		if("03".equalsIgnoreCase(cusData.getCustomerStatus())) {
			cusData.setApprovedBy(getUserId());
		}
		serviceResult = customerService.updateCustomerStatus(cusData);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/verifyRequest", produces = "application/json")
	public ApiResponse<CustomerVerifyData> verifyRequest(@RequestBody ApiRequest<CustomerData> request) {
		ServiceResult<CustomerVerifyData> serviceResult = customerService.verifyRequest(request.getData());
		if (serviceResult.isSuccess()) {
			CustomerVerifyData customerVerify = serviceResult.getResult();
			log.info("CustomerVerifyData : "+customerVerify);

			return ApiResponse.success(customerVerify);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	@WritePermission
	@PostMapping(value = "/verifyValidate", produces = "application/json")
	public ApiResponse<String> verifyValidate(@RequestBody ApiRequest<CustomerVerifyData> request) {
		ServiceResult<String> serviceResult = customerService.verifyValidate(request.getData());
		if (serviceResult.isSuccess()) {
			String customerVerify = serviceResult.getResult();
			log.info("CustomerVerifyData : "+customerVerify);

			return ApiResponse.success(customerVerify);			
		} else {
			return ApiResponse.fail();
		}
	}

	//=========================================================================================================
	//===================================================== Case ==============================================
	//=========================================================================================================
	@ReadPermission
	@PostMapping(value = "/getCustomerCaseList", produces = "application/json")
	public ApiPageResponse<List<CaseModelBean>> getCustomerCaseList(@RequestBody ApiPageRequest<CaseModelBean> request) {
		log.info("CaseModelBean : " + request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CaseModelBean>> serviceResult = customerService.getCustomerCaseList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@PostMapping(value = "/getCustomerSrList", produces = "application/json")
	public ApiPageResponse<List<ServiceRequestModelBean>> getCustomerSrList(@RequestBody ApiPageRequest<ServiceRequestModelBean> request) {
		log.info("ServiceRequestModelBean : " + request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ServiceRequestModelBean>> serviceResult = customerService.getCustomerSrList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ReadPermission
	@PostMapping(value = "/getCustomerByCitizenIdOrPassportNo", produces = "application/json")
	public ApiResponse<List<CustomerData>> getCustomerByCitizenIdOrPassportNo(
			@RequestBody ApiPageRequest<CustomerListCriteria> request) {
		CustomerListCriteria criteria = request.getData();
		criteria.setBuId(getBuId());
		ServiceResult<List<CustomerData>> serviceResult = customerService.findCustomerByCitizenIdOrPassportNo(criteria);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	
	@ReadPermission
	@PostMapping(value = "/getCustomerByPhoneNo", produces = "application/json")
	public ApiResponse<List<CustomerData>> getCustomerByPhoneNo(@RequestBody ApiRequest<CustomerListCriteria> request) {
		CustomerListCriteria criteria = request.getData();
		criteria.setBuId(getBuId());
		
		
		ServiceResult<List<CustomerData>> serviceResult = customerService.getCustomerByPhoneNo(criteria);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@GetMapping(value = "/profile_image/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) {
		log.info("getProfileImage: "+profileImagePath + File.separator + fileName);
		Resource file = fileService.loadFile(profileImagePath + File.separator + fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	
	
	@WritePermission
	@PostMapping(value = "/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("customerId") Long customerId) {
		String message = "";
	 
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String fileName = customerId + "_"+timeStamp+ CommonUtil.getFileExtension(file);
			fileService.saveFile(file, profileImagePath, fileName);
			customerService.updateCustomerProfileImage(fileName, customerId);
			return ResponseEntity.status(HttpStatus.OK).body(fileName);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
}
