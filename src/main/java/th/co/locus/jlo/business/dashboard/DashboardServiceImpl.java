package th.co.locus.jlo.business.dashboard;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.dashboard.bean.DashboardBean;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

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
				
}
