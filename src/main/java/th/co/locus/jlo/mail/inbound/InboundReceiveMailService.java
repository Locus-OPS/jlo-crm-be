package th.co.locus.jlo.mail.inbound;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.SearchInboundReceiveMailBean;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

public interface InboundReceiveMailService {

	public ServiceResult<InboundReceiveMailBean> insertEmailInbound(InboundReceiveMailBean bean);

	public ServiceResult<Page<InboundReceiveMailBean>> getEmailInboundList(SearchInboundReceiveMailBean bean,
			PageRequest pageRequest);

	public ServiceResult<InboundReceiveMailBean> getEmailInboundData(InboundReceiveMailBean bean);
	
	
	public ServiceResult<InboundReceiveMailBean> createIbEmailAttachment(InboundReceiveMailBean bean,FileModelBean fileBean, MultipartFile file) throws IOException ;
}
