/**
 * 
 */
package th.co.locus.jlo.business.cases.casesatt.bean;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

/**
 * @author Mr.BoonOom
 *
 */
@Data
public class CaseAttachmentModelBean extends BaseModelBean {

	private Long caseAttId;
	private Long attachmentId;
	private String caseNumber;
	private String fileName;
	private String filePath;
	private String fullPath;
	
	private String fileExtension;
	private Long fileSize;

	@JsonIgnore
	private MultipartFile file;

}
