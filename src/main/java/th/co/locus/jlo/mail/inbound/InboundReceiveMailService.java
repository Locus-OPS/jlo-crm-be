package th.co.locus.jlo.mail.inbound;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.SearchInboundReceiveMailBean;

public interface InboundReceiveMailService {

	public ServiceResult<InboundReceiveMailBean> insertEmailInbound(InboundReceiveMailBean bean);

	public ServiceResult<Page<InboundReceiveMailBean>> getEmailInboundList(SearchInboundReceiveMailBean bean,
			PageRequest pageRequest);

	public ServiceResult<InboundReceiveMailBean> getEmailInboundData(InboundReceiveMailBean bean);

	 

	public ServiceResult<InboundReceiveMailBean> updateEmailInbound(InboundReceiveMailBean bean);


	 public void assignEmailToCase() ;
	

}
