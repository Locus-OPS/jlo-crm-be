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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.consulting.bean.ConsultingModelBean;
import th.co.locus.jlo.business.dashboard.bean.DashboardBean;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;

@Slf4j
@Api(value = "API for Consulting ", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/dashboard")
public class DashboardController extends BaseController {

	@Autowired
	private DashboardService dashboardService;

	@ApiOperation(value = "Get Count Case Each Status")
	@PostMapping(value = "/getCountCaseEachStatus", produces = "application/json")
	public ApiResponse<DashboardBean> getCountCaseEachStatus(@RequestBody ApiRequest<DashboardBean> request) {
		try {

			StringUtil.nullifyObject(request.getData());
			DashboardBean dashboardBean = request.getData();
			
			if("01".equals(dashboardBean.getViewBy())) {
				dashboardBean.setOwnerId(getUserId());
				
			}else if("02".equals(dashboardBean.getViewBy())) {
				dashboardBean.setOrgId(getUserId());
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

	@ApiOperation(value = "Get Case Consulting List")
	@PostMapping(value = "/getCaseDashboardList", produces = "application/json")
	public ApiPageResponse<List<CaseModelBean>> getCaseDashboardList(
			@RequestBody ApiPageRequest<DashboardBean> request) {

		StringUtil.nullifyObject(request.getData());
		PageRequest pageRequest = getPageRequest(request);
		DashboardBean bean = new DashboardBean();
		bean = request.getData();
		
		if("01".equals(bean.getViewBy())) {
			bean.setOwnerId(getUserId());
			
		}else if("02".equals(bean.getViewBy())) {
			bean.setOrgId(getUserId());
		}
		
		ServiceResult<Page<CaseModelBean>> serviceResult = dashboardService.getCaseDashboardList(bean, pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
}
