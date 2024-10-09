package th.co.locus.jlo.mail.inbound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.cases.CaseService;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.cases.casesact.CaseActivityService;
import th.co.locus.jlo.business.cases.casesact.bean.CaseActivityModelBean;
import th.co.locus.jlo.business.consulting.ConsultingService;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.business.customer.CustomerService;
import th.co.locus.jlo.business.customer.bean.ContactData;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.CustomerListCriteria;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.SearchInboundReceiveMailBean;

@Slf4j
@Service
public class InboundReceiveMailServiceImpl extends BaseService implements InboundReceiveMailService {

	@Autowired
	private ConsultingService consultingService;

	@Autowired
	private CaseService caseService;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private CaseActivityService caseAct;
	
	
	@Override
	public ServiceResult<InboundReceiveMailBean> insertEmailInbound(InboundReceiveMailBean bean) {

		int result = commonDao.insert("emailInbound.insertEmailInbound", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailInbound.findEmailInboundById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<InboundReceiveMailBean>> getEmailInboundList(SearchInboundReceiveMailBean bean,
			PageRequest pageRequest) {

		return success(commonDao.selectPage("emailInbound.getEmailInboundList", bean, pageRequest));
	}

	@Override
	public ServiceResult<InboundReceiveMailBean> getEmailInboundData(InboundReceiveMailBean bean) {
		// TODO Auto-generated method stub
		return success(commonDao.selectOne("emailInbound.findEmailInboundById", bean));
	}

	@Override
	public ServiceResult<InboundReceiveMailBean> updateEmailInbound(InboundReceiveMailBean bean) {
		int result = commonDao.update("emailInbound.updateEmailInbound", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailInbound.findEmailInboundById", bean));
		}
		return fail();
	}

	@Override
	public void assignEmailToCase() {
		
		try {

			List<InboundReceiveMailBean> emailListAll = commonDao.selectList("emailInbound.getEmailInboundAllList");
			if (emailListAll.size() > 0) {
				for (InboundReceiveMailBean email : emailListAll) {
					log.debug(" email : {}",email);
					CustomerListCriteria criteriaEmail = new CustomerListCriteria(); 
					criteriaEmail.setEmail(email.getFormEmail());
					ServiceResult<CustomerData>  custResult =	custService.getCustomerByEmail(criteriaEmail);
					log.debug(" custResult : {}",custResult);
					CustomerData custData = new CustomerData();
					if(custResult.isSuccess()) {
						if(custResult.getResult() != null) {
							custData = new CustomerData();
							//Exiting customer
							custData = custResult.getResult();
							log.debug(" custData : {}",custData);
						}else {
							// New Customer
							custData = new CustomerData();		
							custData.setCreatedBy((long) 41);
							custData.setUpdatedBy((long) 41);
							custData.setBuId(1);
							custData.setCustomerType(true);
							custData.setCustomerStatus("01");
							custData.setFirstName(email.getFormEmail());
							custData.setEmail(email.getFormEmail());
							custData.setRemark("Create from email inbound");							
							ServiceResult<CustomerData>	serviceResult = custService.createCustomer(custData);
							log.debug("serviceResult : {}",serviceResult);
							if(serviceResult.isSuccess()) {
								custData = serviceResult.getResult();
								log.debug("after create customer custData : {}",custData);
								ContactData contData = new ContactData();
								contData.setCustomerId(custData.getCustomerId());
								custService.createContact(contData);
								log.info("Create Contact Success !!");
							}else {
								log.error("Create Customer not success !!");
							}
						}
					}
					
					
					ConsultingModelBean consultingData = new ConsultingModelBean();
					consultingData.setCustomerId(String.valueOf(custData.getCustomerId()));
					consultingData.setTitle(email.getSubjectEmail() != null ? email.getSubjectEmail() : "Email No subject");
					
					consultingData.setStatusCd("02"); // Finish
					consultingData.setConsultingTypeCd("01"); // Inbound
					consultingData.setChannelCd("02"); // Email Inbound
					consultingData.setConsOwnerId((long) 20); // Some Agent > apichat
					consultingData.setCreatedBy((long) 41);
					consultingData.setUpdatedBy((long) 41);
					consultingData.setBuId(1);
					ServiceResult<ConsultingModelBean> serviceResult = consultingService.insertConsultingEmailInbound(consultingData);
							 

					if (serviceResult.isSuccess()) {
						ConsultingModelBean consult = serviceResult.getResult();
						
						CaseModelBean caseBean = new CaseModelBean();
						caseBean.setCustomerId(custData.getCustomerId());
						caseBean.setConsultingNumber(consult.getConsultingNumber());
						caseBean.setSubject(email.getSubjectEmail() != null ? email.getSubjectEmail() : "Email No subject" );
						caseBean.setCaseSlaId("1");
						caseBean.setStatus("01"); // NEW
						caseBean.setDivisionTypeCode("02"); // CASE_DIVISION > Service Request
						caseBean.setType("25"); // CASE_TYPE
						caseBean.setSubType("05");// CASE_SUBTYPE > ติดต่อเรื่องทั่วไป
						caseBean.setPriority("03"); // CASE_PRIORITY >Medium
						caseBean.setChannel("02"); // CASE_CHANNEL 02
						caseBean.setInformName(email.getFormEmail());

						caseBean.setOwner("20");
						caseBean.setOwnerDept("3");
						caseBean.setWorkNote(null);
						caseBean.setDetail("Create From Email Inbound");

						caseBean.setBuId(1);
						caseBean.setCreatedBy((long) 20);
						caseBean.setUpdatedBy((long) 20);
						
						log.info("Subject Email : {} ",email.getSubjectEmail());
						log.info("checkCaseExisting : {} ",checkCaseExisting(email.getSubjectEmail()));
							
						if(checkCaseExisting(email.getSubjectEmail())) {
							//existing case
							String caseNumberFromSub = getCaseNumberFromSubjectMail(email.getSubjectEmail());
							log.info("caseNumberFromSub {}",caseNumberFromSub);	
							caseBean.setCaseNumber(caseNumberFromSub);
//							ServiceResult<CaseModelBean> caseResult = caseService.getCaseByCaseNumber(caseNumberFromSub);
//							CaseModelBean  caseExistBean = caseResult.getResult();
//							CaseModelBean caseUpdateBean = new CaseModelBean();							
//							caseUpdateBean.setCreatedBy((long) 20);
//							caseUpdateBean.setUpdatedBy((long) 20);
//							caseUpdateBean.setCaseNumber(caseNumberFromSub);							
//							caseService.updateCase(caseUpdateBean);
							
							int resultInsert = commonDao.insert("consulting.createRelConsulting", caseBean);
							if(resultInsert > 0) {
								log.info("Case Existing Create consulting relation with case successfull");
							}else {
								log.error("Case Existing Create consulting relation with case successfull");
							}
							
							
							CaseActivityModelBean actBean = new CaseActivityModelBean();
							actBean.setCaseNumber(caseNumberFromSub);
							actBean.setType("04");  	//Follow up
 							actBean.setStatus("01");	//Not Started
							actBean.setSubject("Follow-up Activities");
							actBean.setDetail("Auto Activities");
							actBean.setOwnerCode("20");
							actBean.setBuId(1);
							actBean.setCreatedBy((long) 20);
							actBean.setUpdatedBy((long) 20);
							
							caseAct.createCaseActivity(actBean);
							
							
							
						}else {
							//new case
							log.info("In case Create Case ");
							ServiceResult<CaseModelBean> caseResultInsert = caseService.createCase(caseBean);
							if (caseResultInsert.isSuccess()) {
									 
								int resultInsert = commonDao.insert("consulting.createRelConsulting", caseBean);
								if(resultInsert > 0) {
									log.info("Create consulting relation with case successfull");
								}else {
									log.error("Create consulting relation with case successfull");
								}
							
							}else {
								log.error("Can't create case successful : {} ",caseResultInsert.getResponseCode());
							}
							
						}			
						
						// Update Status email In-bound
						InboundReceiveMailBean emailUpdate = new InboundReceiveMailBean();
						emailUpdate.setId(email.getId());
						emailUpdate.setStatusCd("02"); // MAIL_IB_STATUS 02 Assign
						emailUpdate.setCreatedBy((long) 41);
						emailUpdate.setUpdatedBy((long) 41);
						emailUpdate.setBuId(1);

						int result = commonDao.update("emailInbound.updateEmailInbound", emailUpdate);
						if (result > 0) {
							log.info("Update status email inbound is successfully !!");
						} else {
							log.error("Can't update status email inbound is successfully !!");
						}


					}

				}
			}
		} catch (Exception e) {
			log.error("eror {} :",e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private static boolean checkCaseExisting(String emailSubject) {
		return emailSubject.contains("#CASE");
	}
	
	private static String getCaseNumberFromSubjectMail(String emailSubject) {
		int startIndex = emailSubject.indexOf("#");
		int endIndex = emailSubject.indexOf("]");
		log.info("startIndex {} , endIndex {}", startIndex,endIndex);
		String caseString = emailSubject.substring(startIndex+1, endIndex);
		log.info("Case Number {}",caseString);
		return caseString;
	}

}
