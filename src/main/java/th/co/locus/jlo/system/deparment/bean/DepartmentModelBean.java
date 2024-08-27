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
public class DepartmentModelBean extends BaseModelBean {

	private Long id;
	private String departmentName;
	private String description;
	private String statusCd;
	private String statusName;
}
