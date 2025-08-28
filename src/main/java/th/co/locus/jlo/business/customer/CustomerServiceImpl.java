package th.co.locus.jlo.business.customer;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
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
import th.co.locus.jlo.util.JloMailSender;
import th.co.locus.jlo.common.service.BaseService;

@Slf4j
@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

	@Autowired
	private JloMailSender jloMailSender;

	@Value("${jlo.mail.from}")
	private String mailFrom;

	@Override
	public ServiceResult<Page<CustomerData>> getCustomerList(CustomerListCriteria criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("customer.getCustomerList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<CustomerData> updateCustomer(CustomerData customer) {
		int result = commonDao.update("customer.updateCustomer", customer);
		if (result > 0) {
			return success(commonDao.selectOne("customer.findCustomerById", customer));
		}

		return fail();
	}

	@Override
	public ServiceResult<CustomerData> createCustomer(CustomerData customer) {
		int result = commonDao.update("customer.createCustomer", customer);
		if (result > 0) {
			return success(commonDao.selectOne("customer.findCustomerById", customer));
		}
		return fail();
	}

	@Override
	public ServiceResult<CustomerData> updateCustomerStatus(CustomerData customer) {
		int result = commonDao.update("customer.updateCustomerStatus", customer);
		if (result > 0) {
			return success(commonDao.selectOne("customer.findCustomerById", customer));
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<AddressData>> getCustomerAddressList(AddressData data, PageRequest pageRequest) {
		return success(commonDao.selectPage("customer.getCustomerAddressList", data, pageRequest));
	}

	private void clearAddressPrimary(AddressData addrData) {
		commonDao.update("customer.clearCustomerAddressPrimary", addrData);
	}

	@Override
	public ServiceResult<AddressData> createCustomerAddress(AddressData addrData) {
		if ("Y".equalsIgnoreCase(addrData.getPrimaryYn())) {
			clearAddressPrimary(addrData);
		}
		int result = commonDao.update("customer.createCustomerAddress", addrData);
		if (result > 0) {
			return success(commonDao.selectOne("customer.findCustomerAddressById", addrData));
		}
		return fail();
	}

	@Override
	public ServiceResult<AddressData> updateCustomerAddress(AddressData addrData) {
		if ("Y".equalsIgnoreCase(addrData.getPrimaryYn())) {
			clearAddressPrimary(addrData);
		}
		int result = commonDao.update("customer.updateCustomerAddress", addrData);
		if (result > 0) {
			return success(commonDao.selectOne("customer.findCustomerAddressById", addrData));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteCustomerAddress(AddressData bean) {
		return success(commonDao.delete("customer.deleteCustomerAddress", bean));
	}

	@Override
	public ServiceResult<AddressData> getCustomerAddressByPrimary(AddressData addrData) {
		return success(commonDao.selectOne("customer.findCustomerAddressById", addrData));
	}

	@Override
	public ServiceResult<CustomerData> getCustomerById(CustomerData customer) {
		return success(commonDao.selectOne("customer.findCustomerById", customer));
	}

	@Override
	public ServiceResult<CustomerVerifyData> verifyRequest(CustomerData data) {
		try {
			CustomerVerifyData verify = new CustomerVerifyData();
			String genKey = new String(OTP(6));
			verify.setKey(genKey);
			verify.setVerify(Base64.getEncoder().encodeToString(genKey.getBytes()));
			verify.setEmail(data.getEmail());
			verify.setRef(RandomStringUtils.random(10, true, false));

			String body = "Verify Referance " + verify.getRef() + " Key = " + verify.getKey();
			String title = "";
			if (data.getFirstName() != null && data.getFirstName().length() > 0) {
				title = "Verify Customer " + data.getFirstName() + " " + data.getLastName();
			} else {
				title = "Verify Customer " + data.getBusinessName();
			}
			jloMailSender.sendMail(mailFrom, data.getEmail(), title, body);
			verify.setKey("");
			return success(verify);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return fail();
	}

	private static char[] OTP(int len) {
		String numbers = "0123456789";

		Random rndm_method = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}
		return otp;
	}

	private static boolean checkOTP(CustomerVerifyData data) {
		String verify = new String(Base64.getDecoder().decode(data.getVerify().getBytes()));
		if (data.getKey().equals(verify)) {
			return true;
		}
		return false;
	}

	@Override
	public ServiceResult<String> verifyValidate(CustomerVerifyData data) {
		if (checkOTP(data)) {
			return success("OK");
		} else {
			return success("No");
		}
	}

	@Override
	public ServiceResult<List<CustomerData>> findCustomerByCitizenIdOrPassportNo(CustomerListCriteria criteria) {
		return success(commonDao.selectList("customer.findCustomerByCitizenIdOrPassportNo", criteria));
	}

	@Override
	public ServiceResult<Integer> createContact(ContactData customer) {
		int result = commonDao.update("customer.createContact", customer);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<List<CustomerData>> getCustomerByPhoneNo(CustomerListCriteria criteria) {
		return success(commonDao.selectList("customer.findCustomerByPhoneNo", criteria));
	}

	@Override
	public void updateCustomerProfileImage(String fileName, Long customerId) {
		commonDao.update("customer.updateCustomerProfileImage",
				Map.of("pictureUrl", fileName, "customerId", customerId));
	}

	@Override
	public ServiceResult<Page<CaseModelBean>> getCustomerCaseList(CaseModelBean data, PageRequest pageRequest) {
		return success(commonDao.selectPage("customer.getCustomerCaseList", data, pageRequest));
	}

	@Override
	public ServiceResult<Page<ServiceRequestModelBean>> getCustomerSrList(ServiceRequestModelBean data,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("customer.getCustomerSrList", data, pageRequest));
	}

	@Override
	public ServiceResult<CustomerData> getCustomerByEmail(CustomerListCriteria criteria) {
		return success(commonDao.selectOne("customer.findOneCustomerByEmail", criteria));
	}

	@Override
	public ServiceResult<Page<CustomerAuditLogBean>> getCustomerAuditLogList(CustomerAuditLogBean data,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("customer.getCustomerAuditLogList", data, pageRequest));
	}
}
