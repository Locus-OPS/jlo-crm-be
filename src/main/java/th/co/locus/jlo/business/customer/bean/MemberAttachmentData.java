package th.co.locus.jlo.business.customer.bean;

import java.util.Date;

import lombok.Data;

@Data
public class MemberAttachmentData {

	private Long memberAttId;
	private Integer memberId;
	private Long attId;
	private Integer buId;
	private String title;
	private String descp;
	private String mainFlag;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	
	private String fileName;
	private String filePath;
	private String fileExtension;
	private Integer fileSize;
	
}
