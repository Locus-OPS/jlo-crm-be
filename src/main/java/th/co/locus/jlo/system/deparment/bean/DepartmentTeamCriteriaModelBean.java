package th.co.locus.jlo.system.deparment.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentTeamCriteriaModelBean extends SortingModelBean {

	private String departmentId;
	private String teamName;
	private String statusCd;
	private Integer buId;
}
