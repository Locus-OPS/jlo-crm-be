package th.co.locus.jlo.system.sla.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SLACriteriaModelBean extends SortingModelBean {

	private String slaName;
	private Integer buId;
}
