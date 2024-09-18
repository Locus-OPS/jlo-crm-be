package th.co.locus.jlo.mail.inbound.att;

import java.io.IOException;
import java.util.List;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.mail.inbound.att.bean.InboundAttachmentReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;

public interface InboundAttachmentReceiveMailService {

	 ServiceResult<List<InboundAttachmentReceiveMailBean>> getEmailInboundAttListById(InboundReceiveMailBean bean);

	public ServiceResult<InboundAttachmentReceiveMailBean> createEmailInboundAtt(InboundAttachmentReceiveMailBean bean)
			throws IOException;
	
	 
}
