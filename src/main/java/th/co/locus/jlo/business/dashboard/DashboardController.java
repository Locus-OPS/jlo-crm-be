/**
 * 
 */
package th.co.locus.jlo.business.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.dashboard.bean.DashboardBean;
import th.co.locus.jlo.business.dashboard.bean.DashboardChartsBarBean;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

@Slf4j
@RestController
@RequestMapping("api/dashboard")
public class DashboardController extends BaseController {

	@Autowired
	private DashboardService dashboardService;

	@PostMapping(value = "/getCountCaseEachStatus", produces = "application/json")
	public ApiResponse<DashboardBean> getCountCaseEachStatus(@RequestBody ApiRequest<DashboardBean> request) {
		try {

			CommonUtil.nullifyObject(request.getData());
			DashboardBean dashboardBean = request.getData();

			if ("01".equals(dashboardBean.getViewBy())) {
				// My
				dashboardBean.setOwnerId(getUserId());
			} else if ("02".equals(dashboardBean.getViewBy())) {
				// Team
				System.out.println("getTeamId() "+getTeamId());
				dashboardBean.setTeamId(getTeamId());

			} else if ("03".equals(dashboardBean.getViewBy())) {
				// Department
				System.out.println("getDivId() "+getDivId());
				dashboardBean.setDivId(getDivId());
			}

			ServiceResult<DashboardBean> serviceResult = dashboardService.getCountCaseEachStatus(dashboardBean);

			if (serviceResult.isSuccess()) {
				log.info(serviceResult.getResult().toString());
				return ApiResponse.success(serviceResult.getResult());
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ApiResponse.fail();
	}

	@PostMapping(value = "/getCaseDashboardList", produces = "application/json")
	public ApiPageResponse<List<CaseModelBean>> getCaseDashboardList(
			@RequestBody ApiPageRequest<DashboardBean> request) {

		CommonUtil.nullifyObject(request.getData());
		PageRequest pageRequest = getPageRequest(request);
		DashboardBean bean = new DashboardBean();
		bean = request.getData();

		// My
		if ("01".equals(bean.getViewBy())) {
			bean.setOwnerId(getUserId());
		} else if ("02".equals(bean.getViewBy())) {
			bean.setTeamId(getTeamId());
		} else if ("03".equals(bean.getViewBy())) {
			bean.setDivId(getDivId());
		}

		ServiceResult<Page<CaseModelBean>> serviceResult = dashboardService.getCaseDashboardList(bean, pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@PostMapping(value = "/getChartBarDataList", produces = "application/json")
	public ApiResponse<List<DashboardChartsBarBean>> getChartBarDataList(
			@RequestBody ApiRequest<DashboardBean> request) {

		CommonUtil.nullifyObject(request.getData());
		DashboardBean bean = new DashboardBean();
		bean = request.getData();

		// My
		if ("01".equals(bean.getViewBy())) {
			bean.setOwnerId(getUserId());
		} else if ("02".equals(bean.getViewBy())) {
			bean.setTeamId(getTeamId());
		} else if ("03".equals(bean.getViewBy())) {
			bean.setDivId(getDivId());
		}

		ServiceResult<List<DashboardChartsBarBean>> serviceResult = dashboardService.getChartBarDataList(bean);

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@PostMapping(value = "/getChartPieDataList", produces = "application/json")
	public ApiResponse<List<DashboardChartsBarBean>> getChartPieDataList(
			@RequestBody ApiRequest<DashboardBean> request) {

		CommonUtil.nullifyObject(request.getData());
		DashboardBean bean = new DashboardBean();
		bean = request.getData();

		// My
		if ("01".equals(bean.getViewBy())) {
			bean.setOwnerId(getUserId());
		} else if ("02".equals(bean.getViewBy())) {
			bean.setTeamId(getTeamId());
		} else if ("03".equals(bean.getViewBy())) {
			bean.setDivId(getDivId());
		}

		ServiceResult<List<DashboardChartsBarBean>> serviceResult = dashboardService.getChartPieDataList(bean);

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

}
