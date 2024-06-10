package th.co.locus.jlo.business.loyalty.attachment;

import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;

@Service
public class AttachmentMgntServiceImpl extends BaseService implements AttachmentMgntService {

	@Override
	public ServiceResult<FileModelBean> getAttachmentByAttId(Long attId) {

		return success(commonDao.selectOne("attmgnt.getAttachmentByAttId", Map.of("attId", attId)));
	}

}
