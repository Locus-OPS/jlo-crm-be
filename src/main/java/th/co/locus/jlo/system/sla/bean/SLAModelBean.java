package th.co.locus.jlo.system.sla.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SLAModelBean extends BaseModelBean {

	private Long slaId;
	private String slaName;
	private String slaUnit;
	private String statusCd;
	private String statusName;
	private String descp;

}
