package th.co.locus.jlo.business.customer;

import java.util.List;

import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.ContactData;
import th.co.locus.jlo.business.customer.bean.CustomerAuditLogBean;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.CustomerListCriteria;
import th.co.locus.jlo.business.customer.bean.CustomerVerifyData;
import th.co.locus.jlo.business.sr.bean.ServiceRequestModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface CustomerService {

	public ServiceResult<Page<CustomerData>> getCustomerList(CustomerListCriteria criteria, PageRequest pageRequest);
	public ServiceResult<CustomerData> getCustomerById(CustomerData customer);
	public ServiceResult<CustomerData> updateCustomer(CustomerData customer);
	public ServiceResult<CustomerData> createCustomer(CustomerData customer);
	public ServiceResult<CustomerData> updateCustomerStatus(CustomerData cusData);
	
	public ServiceResult<Page<CaseModelBean>> getCustomerCaseList(CaseModelBean data, PageRequest pageRequest);
	
	
	public ServiceResult<Page<AddressData>> getCustomerAddressList(AddressData data, PageRequest pageRequest);
	public ServiceResult<AddressData> createCustomerAddress(AddressData addrData);
	public ServiceResult<AddressData> updateCustomerAddress(AddressData addrData);
	public ServiceResult<Integer> deleteCustomerAddress(AddressData bean);
	public ServiceResult<AddressData> getCustomerAddressByPrimary(AddressData addrData);
	public ServiceResult<CustomerVerifyData> verifyRequest(CustomerData data);
	public ServiceResult<String> verifyValidate(CustomerVerifyData data);
	public ServiceResult<List<CustomerData>> findCustomerByCitizenIdOrPassportNo(CustomerListCriteria criteria);
	
	// Create Contact
	ServiceResult<Integer> createContact(ContactData customer);
	
	
	public ServiceResult<List<CustomerData>> getCustomerByPhoneNo(CustomerListCriteria criteria);
	
	public void updateCustomerProfileImage(String fileName, Long customerId);
	
	//Service Request
	public ServiceResult<Page<ServiceRequestModelBean>> getCustomerSrList(ServiceRequestModelBean data, PageRequest pageRequest);
	
	
	public ServiceResult<CustomerData> getCustomerByEmail(CustomerListCriteria criteria);
	
	public ServiceResult<Page<CustomerAuditLogBean>> getCustomerAuditLogList(CustomerAuditLogBean data, PageRequest pageRequest);
}
