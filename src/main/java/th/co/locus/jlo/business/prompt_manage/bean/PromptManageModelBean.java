package th.co.locus.jlo.business.prompt_manage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class PromptManageModelBean extends BaseModelBean {

	private String codeId;
	private String codeType;
	private String codeName;
	private String parentType;
	private String parentId;
	private String lang;
	private Integer seq;
	private String etc1;
	private String etc2;
	private String etc3;
	private String etc4;
	private String etc5;
	private String description;
	private String activeFlag;
	private String syncFlag;
	
}
