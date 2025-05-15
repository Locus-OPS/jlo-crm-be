package th.co.locus.jlo.business.prompt_manage.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class PromptManageCriteriaModelBean extends SortingModelBean {

	private String codeId;
	private String codeType;
	private String codeName;
	private List<String> codeTypeList;
	private String activeFlag;
	
}
