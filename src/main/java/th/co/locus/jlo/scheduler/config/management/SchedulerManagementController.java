package th.co.locus.jlo.scheduler.config.management;

import java.util.List;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerBean;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerHistoryBean;
import th.co.locus.jlo.scheduler.config.management.bean.SearchSchedulerCriteriaModelBean;

@Slf4j
@RestController
@RequestMapping("api/scheduler")
public class SchedulerManagementController extends BaseController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping(value = "/getSchedulerJobs", produces = "application/json")
    public ApiPageResponse<List<SchedulerBean>> getSchedulerJobs(@RequestBody ApiPageRequest<SearchSchedulerCriteriaModelBean> request) {
        PageRequest pageRequest = getPageRequest(request);
        final ServiceResult<Page<SchedulerBean>> serviceResult = schedulerService.findAllScheduler(pageRequest);
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        }

        return ApiPageResponse.fail();
    }
	
	@PostMapping(value = "/schedulerHistoryDetail", produces = "application/json")
    public ApiPageResponse<List<SchedulerHistoryBean>> schedulerHistoryDetail(@RequestBody ApiPageRequest<SchedulerHistoryBean> request) {
        final long id = request.getData().getId();
        PageRequest pageRequest = getPageRequest(request);
        final ServiceResult<Page<SchedulerHistoryBean>> serviceResult = schedulerService.findSchedulerHistoryBySchedulerId(id, pageRequest);
        if (serviceResult.isSuccess()) {
        	return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        }

        return ApiPageResponse.fail();
    }
	
	@PostMapping(value = "/getSchedulerById", produces = "application/json")
    public ApiResponse<SchedulerBean> getSchedulerById(@RequestBody ApiRequest<Long> request) {
        final long id = request.getData();
        final ServiceResult<SchedulerBean> serviceResult = schedulerService.findSchedulerById(id);
        return (ApiResponse<SchedulerBean>) ApiResponse.success(serviceResult.getResult());
    }
	
	@PostMapping(value = "/executeNow",produces = "application/json")
    public ApiResponse<String> executeNow(@RequestBody ApiRequest<Long> request) {
        try {
            final ServiceResult<SchedulerBean> result = schedulerService.findSchedulerById(request.getData());
            if (result.isSuccess()) {
                schedulerService.executeJob(result.getResult());
                return ApiResponse.success("Process");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ApiResponse.fail();
    }

	@PostMapping(value = "/saveSchedulerJob",produces = "application/json")
    public ApiResponse<SchedulerBean> saveSchedulerJob(@RequestBody ApiRequest<SchedulerBean> request) {
		try {
        	SchedulerBean schedulerBean = request.getData();
            if (!CronExpression.isValidExpression(schedulerBean.getExpression())) {
                schedulerBean.setExpression("INVALID EXPRESSION");
                schedulerBean.setUseYn("N");
            }
            if (schedulerBean.getId()==null) {
                schedulerBean.setCreatedBy(getUserId());
                schedulerBean.setUpdatedBy(getUserId());
                final ServiceResult<SchedulerBean> serviceResult = schedulerService.insertScheduler(schedulerBean);
                if (serviceResult.isSuccess()) {
                    schedulerBean = serviceResult.getResult();
                }
            } else {
                schedulerBean.setUpdatedBy(getUserId());
                ServiceResult<SchedulerBean> serviceResult = schedulerService.updateScheduler(schedulerBean);
                if(serviceResult.isSuccess()) {
                	schedulerBean = serviceResult.getResult();
                }
            }
            schedulerService.scheduleJob(schedulerBean);
            
            return ApiResponse.success(schedulerBean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
		
        return ApiResponse.fail();
    }

	@PostMapping(value = "/deleteJob",produces = "application/json")
    public ApiResponse<String> deleteJob(@RequestBody ApiRequest<Long> request) {
        try {
            final ServiceResult<Boolean> result = schedulerService.deleteScheduler(request.getData());
            if (result.isSuccess()) {
                return ApiResponse.success("Success");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return ApiResponse.fail();
    }
}
