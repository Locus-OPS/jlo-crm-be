package th.co.locus.jlo.scheduler.config.management;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.scheduler.config.QuartzCustomListener;
import th.co.locus.jlo.scheduler.config.SchedulerConstant;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerBean;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerHistoryBean;

@Slf4j
@Service
public class SchedulerServiceImpl extends BaseService implements SchedulerService {

    final static String SQL_FIND_ALL_SCHEDULER = "scheduler.management.FIND_ALL_SCHEDULER";
    final static String SQL_FIND_SCHEDULER_BY_ID = "scheduler.management.FIND_SCHEDULER_BY_ID";
    final static String SQL_FIND_SCHEDULER_HISTORY_BY_SCHEDULER_ID = "scheduler.management.FIND_SCHEDULER_HISTORY_BY_SCHEDULER_ID";
    final static String SQL_INSERT_SCHEDULER = "scheduler.management.INSERT_SCHEDULER";
    final static String SQL_INSERT_SCHEDULER_HISTORY = "scheduler.management.INSERT_SCHEDULER_HISTORY";
    final static String SQL_UPDATE_HISTORY = "scheduler.management.UPDATE_SCHEDULER";

    final static private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Value("${scheduler.context}")
    private String schedulerContext;

    @Autowired
    private ServletContext servletContext;

    @Override
    public ServiceResult<List<SchedulerBean>> findAllScheduler() {
        try {
            final List<SchedulerBean> obj = commonDao.selectList(SQL_FIND_ALL_SCHEDULER);
            return success(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public ServiceResult<Page<SchedulerBean>> findAllScheduler(PageRequest pageRequest) {
        try {
            final Page<SchedulerBean> obj = commonDao.selectPage(SQL_FIND_ALL_SCHEDULER,null, pageRequest);
            List<SchedulerBean> runningList = listRunningJobs();
            List<SchedulerBean> allJobsDetailList = listAllJobs();
            for (SchedulerBean s : obj.getContent()) {
                for (SchedulerBean a : allJobsDetailList) {
                    if (s.getId().equals(a.getId())) {
                        if (a.getNextFireTime() != null) {
                            s.setStatus("Next run " + sdf.format(a.getNextFireTime()));
                        }
                    }
                }
                for (SchedulerBean r : runningList) {
                    if (s.getId().equals(r.getId())) {
                        s.setStatus("Running");
                    }
                }
            }
            return success(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public ServiceResult<SchedulerBean> findSchedulerById(Long id) {
        try {
            return success(commonDao.selectOne(SQL_FIND_SCHEDULER_BY_ID,Map.of("id",id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public ServiceResult<Page<SchedulerHistoryBean>> findSchedulerHistoryBySchedulerId(Long schedulerId, PageRequest pageRequest) {
        try {
            return success(commonDao.selectPage(SQL_FIND_SCHEDULER_HISTORY_BY_SCHEDULER_ID, Map.of("schedulerId", schedulerId), pageRequest));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public ServiceResult<SchedulerBean> insertScheduler(SchedulerBean schedulerBean) {
        try {
            final int cnt = commonDao.insert(SQL_INSERT_SCHEDULER, schedulerBean);
            if(cnt>0) {
            	return success(commonDao.selectOne(SQL_FIND_SCHEDULER_BY_ID,Map.of("id",schedulerBean.getId())));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public ServiceResult<SchedulerHistoryBean> insertSchedulerHistory(SchedulerHistoryBean schedulerHistoryModelBean) {
    	try {
            final int cnt = commonDao.insert(SQL_INSERT_SCHEDULER_HISTORY, schedulerHistoryModelBean);
            if(cnt>0) {
            	return success(schedulerHistoryModelBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public ServiceResult<SchedulerBean> updateScheduler(SchedulerBean schedulerBean) {
        try {
            int cnt = commonDao.update(SQL_UPDATE_HISTORY, schedulerBean);
            if(cnt>0) {
            	return success(commonDao.selectOne(SQL_FIND_SCHEDULER_BY_ID,Map.of("id",schedulerBean.getId())));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fail();
    }

    @Override
    public void scheduleJob(SchedulerBean schedulerBean)
            throws ClassNotFoundException, SchedulerException {
        if (!runOnThisContext()) {
            log.info("Scheduler Job not running on this Context (" + servletContext.getContextPath() + ")");
            return;
        }
        if (CronExpression.isValidExpression(schedulerBean.getExpression())) {
            final String jobName = schedulerBean.getSchedulerName();
            final String group = "group_" + jobName;
            final int priority = schedulerBean.getPriority();
            final long jobId = schedulerBean.getId();
            final Class<?> klass = Class.forName(schedulerBean.getClasspath());
            final JobDetail jobDetail = createJob(jobName, group, klass, jobId);
            final Trigger trigger = newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobName, group)
                    .withPriority(priority)
                    .withSchedule(cronSchedule(schedulerBean.getExpression()).inTimeZone(TimeZone.getTimeZone("GMT+7")))
                    .build();
            if (schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(jobName, group))) {
                log.debug("Delete Job : " + jobName);
                schedulerFactoryBean.getScheduler().unscheduleJob(trigger.getKey());
                schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobName, group));
            }
            if (!active(schedulerBean)) {
                log.debug("Job " + schedulerBean.getSchedulerName() + " is not activated");
                return;
            }
            setJobDataMap(jobDetail, schedulerBean);
            log.debug("add job with expression : " + schedulerBean.getExpression());
            schedulerFactoryBean.getScheduler().addJob(jobDetail, true, true);
            schedulerFactoryBean.getScheduler().scheduleJob(trigger);
            schedulerFactoryBean.getScheduler().getListenerManager()
                    .addJobListener(new QuartzCustomListener(jobName), KeyMatcher.keyEquals(jobDetail.getKey()));
        }
    }

    private boolean active(SchedulerBean schedulerBean) {
        return schedulerBean.getUseYn().equalsIgnoreCase("Y") && getHostName().equals(schedulerBean.getRunonInstance());
    }

    private String getHostName() {
        try {
        	log.info("Instance : "+InetAddress.getLocalHost().getHostName());
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
        	log.error(ex.getMessage(),ex);
            return "";
        }
    }

    @Override
    public void cancelJob(SchedulerBean schedulerBean) throws SchedulerException {
        final String jobName = schedulerBean.getSchedulerName();
        final String group = "group_" + jobName;
        if (schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(jobName, group))) {
            log.debug("Delete Job : " + jobName);
            schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobName, group));
        } else {
            log.debug("Job " + jobName + " is not found");
        }
    }

    @Override
    public void shutdownScheduler() throws SchedulerException {
        log.debug("Shutting down Scheduler");
        boolean waitJobsToComplete = false;
        schedulerFactoryBean.getScheduler().shutdown(waitJobsToComplete);
    }

    @Override
    public void interruptJob(SchedulerBean schedulerBean) throws SchedulerException {
        final String jobName = schedulerBean.getSchedulerName();
        final String group = "group_" + jobName;
        schedulerFactoryBean.getScheduler().interrupt(new JobKey(jobName, group));
    }

    @Override
    public List<SchedulerBean> listAllJobs() throws SchedulerException {
        final Scheduler scheduler = schedulerFactoryBean.getScheduler();
        final List<SchedulerBean> schedulerBeans = new ArrayList<>();
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                @SuppressWarnings("unchecked")
				final List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                final Date nextFireTime = triggers.iterator().next().getNextFireTime();
                final JobDataMap jobDataMap = schedulerFactoryBean.getScheduler().getJobDetail(jobKey).getJobDataMap();
                final SchedulerBean schedulerBean = new SchedulerBean();
                schedulerBean.setId(jobDataMap.getLong(SchedulerConstant.SCHEDULER_ID));
                schedulerBean.setSchedulerName(jobKey.getName());
                schedulerBean.setGroup(jobKey.getGroup());
                schedulerBean.setNextFireTime(nextFireTime);
                schedulerBeans.add(schedulerBean);
                log.debug("[jobName] : " + jobKey.getName() + " [groupName] : " + jobKey.getGroup() + " - " + nextFireTime);
            }
        }
        return schedulerBeans;
    }

    @Override
    public List<SchedulerBean> listRunningJobs() throws SchedulerException {
        final List<JobExecutionContext> jobExecutionContexts
                = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
        final List<SchedulerBean> schedulerBeans = new ArrayList<>();
        for (JobExecutionContext j : jobExecutionContexts) {
            final JobKey jobKey = j.getJobDetail().getKey();
            final JobDataMap jobDataMap = schedulerFactoryBean.getScheduler().getJobDetail(jobKey).getJobDataMap();
            final SchedulerBean schedulerBean = new SchedulerBean();
            schedulerBean.setId(jobDataMap.getLong(SchedulerConstant.SCHEDULER_ID));
            schedulerBean.setSchedulerName(jobKey.getName());
            schedulerBean.setGroup(jobKey.getGroup());
            schedulerBean.setFireTime(j.getFireTime());
            schedulerBean.setNextFireTime(j.getNextFireTime());
            schedulerBeans.add(schedulerBean);
        }
        return schedulerBeans;
    }

    @Override
    public void executeJob(SchedulerBean schedulerBean) throws Exception {
        final Class<?> klass = Class.forName(schedulerBean.getClasspath());
        final String jobName = schedulerBean.getSchedulerName() + "_onetime";
        final String group = "group_" + jobName;
        final long jobId = schedulerBean.getId();
        final JobDetail jobDetail = createJob(jobName, group, klass, jobId);

        final Trigger trigger = newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobName, group)
                .withPriority(100)
                .startNow()
                .build();

        if (schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(jobName, group))) {
            log.debug("Job " + jobName + " is running");
        } else {
            log.debug("adding one time job : " + jobName);
            schedulerFactoryBean.getScheduler().addJob(jobDetail, true, true);
            schedulerFactoryBean.getScheduler().scheduleJob(trigger);
            schedulerFactoryBean.getScheduler().getListenerManager()
                    .addJobListener(new QuartzCustomListener(jobName), KeyMatcher.keyEquals(jobDetail.getKey()));
            jobDetail.getJobDataMap().put(SchedulerConstant.MANUAL_EXECUTE, true);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JobDetail createJob(String jobName, String groupName, Class jobClass, long jobId) {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey(jobName, groupName));
        jobDetail.setJobClass(jobClass);
        jobDetail.getJobDataMap().put(SchedulerConstant.RETRY_COUNT, 0);
        jobDetail.getJobDataMap().put(SchedulerConstant.SCHEDULER_ID, jobId);
        return jobDetail;
    }

    private void setJobDataMap(JobDetail jobDetail, SchedulerBean schedulerBean) {
        jobDetail.getJobDataMap().put(SchedulerConstant.EXPRESSION_ORIGINAL, schedulerBean.getExpression());
        jobDetail.getJobDataMap().put(SchedulerConstant.PRIORITY, schedulerBean.getPriority());
        jobDetail.getJobDataMap().put(SchedulerConstant.RETRY_FLAG, false);
//        final Integer delay = schedulerBean.getRetryDelay() == null ? 0 : schedulerBean.getRetryDelay();
//        jobDetail.getJobDataMap().put(SchedulerConstant.RETRY_DELAY, delay);
//        final Integer max = schedulerBean.getRetryMax() == null ? 0 : schedulerBean.getRetryMax();
//        jobDetail.getJobDataMap().put(SchedulerConstant.RETRY_MAX, max);

    }

    @Override
    public ServiceResult<Boolean> deleteScheduler(Long id) {
        try {
            int cnt = commonDao.update("SCHEDULER.DELETE_SCHEDULER", Map.of("id", id));
            if(cnt>0) {
            	return success(true);
            }
        } catch (Exception ex) {
        	log.error(ex.getMessage(),ex);
        }
        return fail();
    }

    @Override
    public boolean runOnThisContext() {
        try {
            return schedulerContext.equals(servletContext.getContextPath());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }
}
