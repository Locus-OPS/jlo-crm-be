/**
 * 
 */
package th.co.locus.jlo.system.deparment.bean;

import lombok.Data;
import th.co.locus.jlo.common.bean.BaseModelBean;

/**
 * 
 */
@Data
public class DepartmentTeamModelBean extends BaseModelBean {

	private Long id;
	private String departmentId;
	private String teamName;
	private String statusCd;
	private String statusName;
	private String description;
	private String departmentName;

}
