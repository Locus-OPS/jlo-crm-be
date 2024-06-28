/**
 * 
 */
package th.co.locus.jlo.business.dashboard;

import th.co.locus.jlo.business.dashboard.bean.DashboardBean;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

/**
 * 
 */
public interface DashboardService {
	public ServiceResult<DashboardBean> getCountCaseEachStatus(DashboardBean dashboardBean);
	
	public ServiceResult<Page<CaseModelBean>> getCaseDashboardList(DashboardBean req,
			PageRequest pageRequest);
}
