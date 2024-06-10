package th.co.locus.jlo.business.customer;

import java.util.List;

import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.CustomerListCriteria;
import th.co.locus.jlo.business.customer.bean.CustomerVerifyData;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface CustomerService {

	public ServiceResult<Page<CustomerData>> getCustomerList(CustomerListCriteria criteria, PageRequest pageRequest);
	public ServiceResult<CustomerData> getCustomerById(CustomerData customer);
	public ServiceResult<CustomerData> updateCustomer(CustomerData customer);
	public ServiceResult<CustomerData> createCustomer(CustomerData customer);
	public ServiceResult<CustomerData> updateCustomerStatus(CustomerData cusData);

	public ServiceResult<Page<AddressData>> getCustomerAddressList(AddressData data, PageRequest pageRequest);
	public ServiceResult<AddressData> createCustomerAddress(AddressData addrData);
	public ServiceResult<AddressData> updateCustomerAddress(AddressData addrData);
	public ServiceResult<Integer> deleteCustomerAddress(AddressData bean);
	public ServiceResult<AddressData> getCustomerAddressByPrimary(AddressData addrData);
	public ServiceResult<CustomerVerifyData> verifyRequest(CustomerData data);
	public ServiceResult<String> verifyValidate(CustomerVerifyData data);
	public ServiceResult<List<CustomerData>> findCustomerByCitizenIdOrPassportNo(CustomerListCriteria criteria);

}
