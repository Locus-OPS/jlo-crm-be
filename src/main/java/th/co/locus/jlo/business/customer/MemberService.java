package th.co.locus.jlo.business.customer;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.business.customer.bean.AddressData;
import th.co.locus.jlo.business.customer.bean.MemberAttachmentData;
import th.co.locus.jlo.business.customer.bean.MemberCardData;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.customer.bean.MemberPointData;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

public interface MemberService {

	ServiceResult<MemberData> createMember(MemberData mData);

	ServiceResult<MemberData> updateMember(MemberData mData);

	ServiceResult<MemberData> getMemberById(MemberData mData);	

	ServiceResult<Page<AddressData>> getMemberAddressList(AddressData data, PageRequest pageRequest);
	
	public ServiceResult<Integer> deleteMemberAddress(AddressData bean);
	
	public ServiceResult<AddressData> getMemberAddressByPrimary(AddressData addrData);

	ServiceResult<Page<MemberCardData>> getMemberCardList(MemberCardData data, PageRequest pageRequest);

	ServiceResult<MemberCardData> getMemberCardById(MemberCardData mCard);

	ServiceResult<MemberPointData> getMemberPoint(MemberPointData mPoint);
	
	ServiceResult<Page<MemberAttachmentData>> getMemberAttachmentList(MemberAttachmentData data, PageRequest pageRequest);

	ServiceResult<MemberAttachmentData> createAttachment(MemberAttachmentData memberAtt, FileModelBean fileBean, MultipartFile file) throws Exception;

	ServiceResult<MemberAttachmentData> updateAttachment(MemberAttachmentData memberAtt, FileModelBean fileBean, MultipartFile file) throws Exception;

	ServiceResult<Boolean> deleteMemberAttachment(MemberAttachmentData mData);

	ServiceResult<Page<CaseModelBean>> getMemberCaseList(CaseModelBean data, PageRequest pageRequest);

	ServiceResult<AddressData> updateMemberAddress(AddressData addrData);

	ServiceResult<AddressData> createMemberAddress(AddressData addrData);

	ServiceResult<List<SelectorModelBean>> getMemberTierList(MemberCardData data);

	ServiceResult<MemberCardData> saveReIssuesCard(MemberCardData cardData);

	ServiceResult<MemberCardData> saveBlockCard(MemberCardData cardData);

}
