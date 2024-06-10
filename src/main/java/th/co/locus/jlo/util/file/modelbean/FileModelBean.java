package th.co.locus.jlo.util.file.modelbean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileModelBean extends BaseModelBean {

	private Long attId;
	private String filePath;
	private String fileName;
	private String fileExtension;
	private Long fileSize;
	
}
