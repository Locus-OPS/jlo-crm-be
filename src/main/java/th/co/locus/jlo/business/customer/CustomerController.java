package th.co.locus.jlo.business.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.ContactData;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.CustomerListCriteria;
import th.co.locus.jlo.business.customer.bean.CustomerVerifyData;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ExtraPermission;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;

@Slf4j
@Api(value = "API for Customer Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/customer")
public class CustomerController extends BaseController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MemberService memberService;

	/**
	 * Receives a customer list.
	 * Adds logs to track from sometime cannot return a result.
	 * @param request the criteria to search
	 * @return the customer list
	 */
	@ApiOperation(value = "Get Customer List")
	@PostMapping(value = "/getCustomerList", produces = "application/json")
	public ApiPageResponse<List<CustomerData>> getCustomerList(
			@RequestBody ApiPageRequest<CustomerListCriteria> request) {
		log.info("getCustomerList CustomerListCriteria : " + request.getData());

		PageRequest pageRequest = getPageRequest(request);
		CustomerListCriteria criteria = request.getData();
		StringUtil.nullifyObject(criteria);
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
	@ApiOperation(value = "Create or Update Customer")
	@PostMapping(value = "/saveCustomer", produces = "application/json")
	public ApiResponse<CustomerData> saveCustomer(@RequestBody ApiRequest<CustomerData> request) {
		StringUtil.nullifyObject(request.getData());
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
	@ApiOperation(value = "Get Customer Address List")
	@PostMapping(value = "/getCustomerAddressList", produces = "application/json")
	public ApiPageResponse<List<AddressData>> getCustomerAddressList(@RequestBody ApiPageRequest<AddressData> request) {
		log.info("CustomerId : "+request.getData());
		
		StringUtil.nullifyObject(request.getData());
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
	@ApiOperation(value = "Create or Update Customer Address")
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
	@ApiOperation(value = "Delete Customer Address")
	@PostMapping(value = "/deleteCustomerAddress", produces = "application/json")
	public ApiResponse<Integer> deleteCustomerAddress(@RequestBody ApiRequest<AddressData> request) {
		return ApiResponse.success(customerService.deleteCustomerAddress(request.getData()).getResult());
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Customer Address By Primary")
	@PostMapping(value = "/getCustomerAddressPrimary", produces = "application/json")
	public ApiResponse<AddressData> getCustomerAddressPrimary(@RequestBody ApiPageRequest<AddressData> request) {
		log.info("CustomerListCriteria : "+request.getData());
		AddressData addressCri = request.getData();
		StringUtil.nullifyObject(addressCri);
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
	@ApiOperation(value = "Get Customer By Id")
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
	@ApiOperation(value = "Create or Update Customer Status")
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
	@ApiOperation(value = "Get Customer Verify Requests")
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
	@ApiOperation(value = "Get Customer Verify Validate")
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

	@ExtraPermission
	@ApiOperation(value = "Create Member")
	@PostMapping(value = "/createMember", produces = "application/json")
	public ApiResponse<MemberData> createMember(@RequestBody ApiRequest<MemberData> request) {
		MemberData mData = request.getData();
		StringUtil.nullifyObject(mData);
		mData.setCreatedBy(getUserId());
		mData.setUpdatedBy(getUserId());
		mData.setApprovedBy(getUserId());
		mData.setBuId(getBuId());
		log.debug(mData.toString());
		ServiceResult<MemberData> serviceResult = memberService.createMember(mData);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	//=========================================================================================================
	//===================================================== Case ==============================================
	//=========================================================================================================
	@ReadPermission
	@ApiOperation(value = "Get Customer Case List")
	@PostMapping(value = "/getCustomerCaseList", produces = "application/json")
	public ApiPageResponse<List<CaseModelBean>> getCustomerCaseList(@RequestBody ApiPageRequest<CaseModelBean> request) {
		log.info("CaseModelBean : " + request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CaseModelBean>> serviceResult = memberService.getMemberCaseList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());			
		} else {
			return ApiPageResponse.fail();
		}
	}

	@ReadPermission
	@ApiOperation(value = "Find Customer by citizen id or passport no.")
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
	@ApiOperation(value = "Find Customer by phone no")
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
}
