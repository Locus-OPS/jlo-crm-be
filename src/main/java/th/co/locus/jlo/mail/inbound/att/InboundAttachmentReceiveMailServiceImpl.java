package th.co.locus.jlo.mail.inbound.att;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.mail.inbound.att.bean.InboundAttachmentReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;

@Slf4j
@Service
public class InboundAttachmentReceiveMailServiceImpl extends BaseService
		implements InboundAttachmentReceiveMailService {

	@Override
	public ServiceResult<InboundAttachmentReceiveMailBean> createEmailInboundAtt(InboundAttachmentReceiveMailBean bean)
			throws IOException {

		int result = commonDao.insert("emailAttInbound.createEmailInboundAtt", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emailAttInbound.findEmailInboundAttById", bean));
		}

		return fail();

	}

	@Override
	public ServiceResult<List<InboundAttachmentReceiveMailBean>> getEmailInboundAttListById(
			InboundReceiveMailBean bean) {
		return success(commonDao.selectList("emailAttInbound.getEmailInboundAttListByParent", bean));
	}

}
