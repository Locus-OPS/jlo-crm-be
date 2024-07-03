package th.co.locus.jlo.kb.modelbean;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class KbDocumentModelBean extends BaseModelBean {
	
	private Long contentAttId;
	private Long attId;
	private Long contentId;
	private String title;
	private String descp;
	private String mainFlag;
	
	private String filePath;
	private String fileName;
	private String fileExtension;
	private Long fileSize;
	
	@JsonIgnore
	private MultipartFile file;

}
