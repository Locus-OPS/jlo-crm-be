package th.co.locus.jlo.business.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.dashboard.bean.DashboardBean;
import th.co.locus.jlo.business.dashboard.bean.DashboardChartsBarBean;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Slf4j
@Service
public class DashboardServiceImpl extends BaseService implements DashboardService {@Override
	public ServiceResult<DashboardBean> getCountCaseEachStatus(DashboardBean dashboardBean) {
		return success(commonDao.selectOne("dashboard.getCountCaseEachStatus",dashboardBean));
	
	}

@Override
public ServiceResult<Page<CaseModelBean>> getCaseDashboardList(DashboardBean req, PageRequest pageRequest) {
	return success(commonDao.selectPage("dashboard.getCaseDashboardList", req, pageRequest));
}

@Override
public ServiceResult<List<DashboardChartsBarBean>> getChartBarDataList(DashboardBean criteria) {
	return success(commonDao.selectList("dashboard.getChartBarDataList", criteria));
}

@Override
public ServiceResult<List<DashboardChartsBarBean>> getChartPieDataList(DashboardBean criteria) {
	return success(commonDao.selectList("dashboard.getChartPieDataList", criteria));
}
				
}
