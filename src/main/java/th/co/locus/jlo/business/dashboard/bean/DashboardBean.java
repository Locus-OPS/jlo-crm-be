package th.co.locus.jlo.business.dashboard.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class DashboardBean extends BaseModelBean {
	
	private int countNew;
	private int countWorking;
	private int countEscalated;
	private int countClosed;
	
	private String viewBy;
	private String ownerId;
	private String orgId;
	
	

}
