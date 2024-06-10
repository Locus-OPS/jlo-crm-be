package th.co.locus.jlo.system.codebook.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCodebookCriteriaModelBean extends SortingModelBean {

	private String codeId;
	private String codeType;
	private String codeName;
	private String buId;
	private String activeFlag;
	
}
