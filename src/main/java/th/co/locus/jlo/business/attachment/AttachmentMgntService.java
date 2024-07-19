package th.co.locus.jlo.business.attachment;

import java.util.List;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

public interface AttachmentMgntService {
	public ServiceResult<FileModelBean> getAttachmentByAttId(Long attId);
	public ServiceResult<List<FileModelBean>> getAttachmentListByRefId(Long refId);
}
