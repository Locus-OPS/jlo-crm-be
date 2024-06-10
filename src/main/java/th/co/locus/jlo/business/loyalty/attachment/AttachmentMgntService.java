package th.co.locus.jlo.business.loyalty.attachment;

import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;

public interface AttachmentMgntService {
	public ServiceResult<FileModelBean> getAttachmentByAttId(Long attId);
}
