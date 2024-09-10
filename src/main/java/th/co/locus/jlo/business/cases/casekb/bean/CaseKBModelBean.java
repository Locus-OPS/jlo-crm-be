package th.co.locus.jlo.business.cases.casekb.bean;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;
@Data
public class CaseKBModelBean extends BaseModelBean {
	private Long id;
	private String caseNumber;
	private Long kbId;
	private String kbTitle;
	private String remark;
	private String url;
	private String description;
}
