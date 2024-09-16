package th.co.locus.jlo.mail.inbound.att.bean;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
public class InboundAttachmentReceiveMailBean extends BaseModelBean {

	
	private Long id;
	private Long attachmentId;
	private Long emailInboundId;
	private String fileName;
	private String filePath;
	private String fullPath;
	
	private String fileExtension;
	private Long fileSize;
	
}
