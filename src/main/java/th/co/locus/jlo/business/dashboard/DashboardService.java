/**
 * 
 */
package th.co.locus.jlo.business.dashboard;

import java.util.List;

import th.co.locus.jlo.business.dashboard.bean.DashboardBean;
import th.co.locus.jlo.business.dashboard.bean.DashboardChartsBarBean;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

/**
 * 
 */
public interface DashboardService {
	public ServiceResult<DashboardBean> getCountCaseEachStatus(DashboardBean dashboardBean);

	public ServiceResult<Page<CaseModelBean>> getCaseDashboardList(DashboardBean req, PageRequest pageRequest);

	public ServiceResult<List<DashboardChartsBarBean>> getChartBarDataList(DashboardBean criteria);

	public ServiceResult<List<DashboardChartsBarBean>> getChartPieDataList(DashboardBean criteria);
}
