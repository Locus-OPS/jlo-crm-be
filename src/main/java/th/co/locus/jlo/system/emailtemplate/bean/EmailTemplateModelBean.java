/**
 * 
 */
package th.co.locus.jlo.system.emailtemplate.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

/**
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EmailTemplateModelBean extends BaseModelBean {

	private long id;
	private long attId;
	private String module;
	private String moduleName;
	private String templateName;
	private String statusCd;
	private String statusName;
	private String description;
	private String templateHtmlCode;

	private String filePath;
	private String fileName;
	private String fileExtension;
	private Long fileSize;

}
