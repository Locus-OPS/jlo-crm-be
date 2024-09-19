package th.co.locus.jlo.scheduler.config.management.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchSchedulerCriteriaModelBean extends SortingModelBean{
	
	private String schedulerName;
	private String useYn;
	
}
