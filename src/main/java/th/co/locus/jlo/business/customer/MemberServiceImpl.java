package th.co.locus.jlo.business.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.MemberAttachmentData;
import th.co.locus.jlo.business.customer.bean.MemberCardData;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.customer.bean.MemberPointData;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.loyalty.engine.EngineTxnService;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionEnrollModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchTransactionCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.file.FileService;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;
@Slf4j
@Service
public class MemberServiceImpl extends BaseService implements MemberService {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private EngineTxnService engineTxnService;
	
	@Override
	@Transactional
	public ServiceResult<MemberData> createMember(MemberData mData) {
		int result = commonDao.insert("member.createMember", mData);
//		int result = 1;
//		mData.setMemberId(1);
		if (result > 0) {
			/* Member Card */			
			Map<String,Object> inMap = new HashMap<String, Object>();
			inMap.put("P_MEMBER_ID", mData.getMemberId());
			inMap.put("P_TIER_ID", null);
			inMap.put("P_CREATED_BY",mData.getCreatedBy());
			inMap.put("P_RE_ISSUE_REASON","");
			inMap.put("P_RE_ISSUE_CARD_NO","");
			inMap.put("O_MEMBER_CARD", "");
			inMap.put("O_TIER_ID","");
			commonDao.selectOne("member.callGenMemberCard",inMap);
			log.debug("MEM : "+inMap.get("O_MEMBER_CARD"));
			/* Address */
			commonDao.insert("member.cloneAddressFromCustomer",mData);			
			/* Member Point */
			commonDao.insert("member.insertMemberPointBalance",mData);
			/* Update Customer Status = 06 */
			CustomerData cData = new CustomerData();
			cData.setCustomerId(mData.getCustomerId());
			cData.setCustomerStatus("06");
			cData.setUpdatedBy(mData.getUpdatedBy());
			commonDao.update("customer.updateCustomerStatus",cData);
			
			MemberData memberData = commonDao.selectOne("member.findMemberById", mData);
			
			// insert engine inital data
			TransactionEnrollModelBean enroll = new TransactionEnrollModelBean();
			enroll.setMemberId(mData.getMemberId().longValue());
			engineTxnService.enrollMember(enroll);
			
			return success(memberData);
		}
		return fail();
	}
	
	@Override
	public ServiceResult<MemberData> updateMember(MemberData mData) {
		int result = commonDao.update("member.updateMember", mData);
		if (result > 0) {
			return success(commonDao.selectOne("member.findMemberById", mData));
		}
		return fail();
	}

	@Override
	public ServiceResult<MemberData> getMemberById(MemberData mData) {
		return success(commonDao.selectOne("member.findMemberById", mData));
	}


	//=========================================================================================================
	//===================================================== Address ===========================================
	//=========================================================================================================
	@Override
	public ServiceResult<Page<AddressData>> getMemberAddressList(AddressData data, PageRequest pageRequest) {
		return success(commonDao.selectPage("member.getMemberAddressList", data, pageRequest));
	}
	
	@Override
	public ServiceResult<AddressData> updateMemberAddress(AddressData addrData) {
		if("Y".equalsIgnoreCase(addrData.getPrimaryYn())) {
			clearAddressPrimary(addrData);
		}
		int result = commonDao.update("member.updateMemberAddress", addrData);
		if (result > 0) {
			return success(commonDao.selectOne("member.findMemberAddressById", addrData));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Integer> deleteMemberAddress(AddressData bean) {
		return success(commonDao.delete("member.deleteMemberAddress", bean));
	}
	
	@Override
	public ServiceResult<AddressData> getMemberAddressByPrimary(AddressData addrData) {
		return success(commonDao.selectOne("member.findMemberAddressById", addrData));
	}

	@Override
	public ServiceResult<AddressData> createMemberAddress(AddressData addrData) {
		if("Y".equalsIgnoreCase(addrData.getPrimaryYn())) {
			clearAddressPrimary(addrData);
		}
		int result = commonDao.insert("member.createMemberAddress", addrData);
		if (result > 0) {
			return success(commonDao.selectOne("member.findMemberAddressById", addrData));
		}
		return fail();
	}
	
	private void clearAddressPrimary(AddressData addrData) {
		commonDao.update("member.clearMemberAddressPrimary",addrData);
	}

	//=========================================================================================================
	//===================================================== Card ==============================================
	//=========================================================================================================
	@Override
	public ServiceResult<Page<MemberCardData>> getMemberCardList(MemberCardData data, PageRequest pageRequest) {
		return success(commonDao.selectPage("member.getMemberCardList", data, pageRequest));
	}

	@Override
	public ServiceResult<MemberCardData> getMemberCardById(MemberCardData mCard) {
		return success(commonDao.selectOne("member.getMemberCardList", mCard));
	}

	@Override
	public ServiceResult<MemberPointData> getMemberPoint(MemberPointData mPoint) {
		return success(commonDao.selectOne("member.getMemberPoint", mPoint));
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getMemberTierList(MemberCardData data) {
		return success(commonDao.selectList("member.getMemberTierList", data));
	}
	
	@Override
	public ServiceResult<MemberCardData> saveReIssuesCard(MemberCardData cardData)  {
		int cnt = commonDao.update("member.inactiveMemberCard", cardData);
		log.info("cnt update inactiveMemberCard "+cnt);
		if(cnt>0) {
			Map<String,Object> inMap = new HashMap<String, Object>();
			inMap.put("P_MEMBER_ID", cardData.getMemberId());
			inMap.put("P_TIER_ID", cardData.getCardTierId());
			inMap.put("P_CREATED_BY",cardData.getCreatedBy());
			inMap.put("P_RE_ISSUE_REASON",cardData.getReIssueReason());
			inMap.put("P_RE_ISSUE_CARD_NO",cardData.getReIssueCardNo());
			inMap.put("O_MEMBER_CARD", "");
			inMap.put("O_TIER_ID","");
			commonDao.selectOne("member.callGenMemberCard",inMap);
			log.debug("O_MEMBER_CARD : "+inMap.get("O_MEMBER_CARD"));
			log.debug("O_TIER_ID : "+inMap.get("O_TIER_ID"));
			cardData.setMemberCardNo(inMap.get("O_MEMBER_CARD").toString());
			cardData.setCardTierId(inMap.get("O_TIER_ID").toString());
			return success(cardData);
		}
		return fail();
	}
	
	@Override
	public ServiceResult<MemberCardData> saveBlockCard(MemberCardData cardData) {
		int result = commonDao.update("member.updateBlockCard", cardData);
		if (result > 0) {
			cardData.setCardStatus("04");
			return success(cardData);
		}
		return fail();
	}


	//=========================================================================================================
	//===================================================== Attachment ========================================
	//=========================================================================================================
	@Override
	@Transactional
	public ServiceResult<MemberAttachmentData> createAttachment(MemberAttachmentData memberAtt, FileModelBean fileBean, MultipartFile file) throws Exception {
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			memberAtt.setAttId(fileBean.getAttId());
		}
		commonDao.insert("member.createMemberAttachment", memberAtt);
		
		return success(commonDao.selectOne("member.findMemberAttachmentById", memberAtt));
	}

	@Override
	@Transactional
	public ServiceResult<MemberAttachmentData> updateAttachment(MemberAttachmentData memberAtt, FileModelBean fileBean, MultipartFile file) throws Exception {
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			memberAtt.setAttId(fileBean.getAttId());
		}
		commonDao.update("member.updateMemberAttachment", memberAtt);
		
		return success(commonDao.selectOne("member.findMemberAttachmentById", memberAtt));		
	}

	@Override
	public ServiceResult<Page<MemberAttachmentData>> getMemberAttachmentList(MemberAttachmentData data,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("member.getMemberAttachmentList", data, pageRequest));
	}

	@Override
	public ServiceResult<Boolean> deleteMemberAttachment(MemberAttachmentData mData) {
		commonDao.delete("member.deleteMemberAttachment",mData);
		return success(true);
	}

	//=========================================================================================================
	//===================================================== Case ==============================================
	//=========================================================================================================
	@Override
	public ServiceResult<Page<CaseModelBean>> getMemberCaseList(CaseModelBean data, PageRequest pageRequest) {
		return success(commonDao.selectPage("member.getMemberCaseList", data, pageRequest));
	}
	

	//=========================================================================================================
	//===================================================== Transaction =======================================
	//=========================================================================================================
	@Override
	public ServiceResult<Page<TransactionModelBean>> getMemberTransactionList(SearchTransactionCriteriaModelBean data,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("member.getMemberTransactionList", data, pageRequest));
	}
}
