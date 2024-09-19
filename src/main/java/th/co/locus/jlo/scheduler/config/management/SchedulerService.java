package th.co.locus.jlo.scheduler.config.management;

import org.quartz.SchedulerException;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerBean;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerHistoryBean;

import java.util.List;

public interface SchedulerService {
    
    ServiceResult<List<SchedulerBean>> findAllScheduler();

    ServiceResult<Page<SchedulerBean>> findAllScheduler(PageRequest pageRequest);

    ServiceResult<SchedulerBean> findSchedulerById(Long id);

    ServiceResult<Page<SchedulerHistoryBean>> findSchedulerHistoryBySchedulerId(Long schedulerId, PageRequest pageRequest);

    ServiceResult<SchedulerBean> insertScheduler(SchedulerBean schedulerBean);

    ServiceResult<SchedulerBean> updateScheduler(SchedulerBean schedulerBean);

    ServiceResult<SchedulerHistoryBean> insertSchedulerHistory(SchedulerHistoryBean schedulerHistoryModelBean);

    void scheduleJob(SchedulerBean schedulerBean) throws ClassNotFoundException, SchedulerException;

    void cancelJob(SchedulerBean schedulerBean) throws SchedulerException;

    void shutdownScheduler() throws SchedulerException;

    void interruptJob(SchedulerBean schedulerBean) throws SchedulerException;

    List<SchedulerBean> listAllJobs() throws SchedulerException;

    List<SchedulerBean> listRunningJobs() throws SchedulerException;
    
    void executeJob(SchedulerBean schedulerBean) throws Exception;
    
    ServiceResult<Boolean> deleteScheduler(Long id);
    
    boolean runOnThisContext();
}
