package th.co.locus.jlo.mail.inbound;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.SearchInboundReceiveMailBean;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Slf4j
@Service
public class InboundReceiveMailServiceImpl extends BaseService implements InboundReceiveMailService {
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<InboundReceiveMailBean> getEmailInboundData(InboundReceiveMailBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<InboundReceiveMailBean> createIbEmailAttachment(InboundReceiveMailBean bean,
			FileModelBean fileBean, MultipartFile file) throws IOException {
		 
		return null;
	}

}
