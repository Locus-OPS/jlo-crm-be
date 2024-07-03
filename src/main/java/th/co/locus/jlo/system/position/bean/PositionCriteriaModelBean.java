package th.co.locus.jlo.system.position.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class PositionCriteriaModelBean extends SortingModelBean {

	private String posName;
	private String parentPosName;
	private Integer buId;
}
