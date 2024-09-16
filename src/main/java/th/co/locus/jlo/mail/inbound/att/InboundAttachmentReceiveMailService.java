package th.co.locus.jlo.mail.inbound.att;

import java.io.IOException;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.mail.inbound.att.bean.InboundAttachmentReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;

public interface InboundAttachmentReceiveMailService {

	public ServiceResult<Page<InboundAttachmentReceiveMailBean>> getEmailInboundAttListById(InboundReceiveMailBean bean,
			PageRequest pageRequest);

	public ServiceResult<InboundAttachmentReceiveMailBean> createEmailInboundAtt(InboundAttachmentReceiveMailBean bean)
			throws IOException;

}
