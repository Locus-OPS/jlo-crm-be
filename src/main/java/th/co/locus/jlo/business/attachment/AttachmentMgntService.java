package th.co.locus.jlo.business.attachment;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

public interface AttachmentMgntService {
	public ServiceResult<FileModelBean> getAttachmentByAttId(Long attId);
}
