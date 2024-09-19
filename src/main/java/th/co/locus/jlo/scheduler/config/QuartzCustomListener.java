package th.co.locus.jlo.scheduler.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.TimeZone;

import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.util.ApplicationContextUtil;
import th.co.locus.jlo.scheduler.config.management.SchedulerService;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerHistoryBean;

@Slf4j
public class QuartzCustomListener implements JobListener {

    private final SchedulerService schedulerService;

    private final String listenerName;

    public QuartzCustomListener(String listenerName) {
        this.listenerName = listenerName;
        schedulerService = ApplicationContextUtil.getApplicationContext().getBean(SchedulerService.class);
    }

    @Override
    public String getName() {
        return listenerName;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.debug("Job to be executed " + context.getJobDetail().getKey().getName());
        context.getJobDetail().getJobDataMap().put(SchedulerConstant.START_DATE, new Date());
        context.getJobDetail().getJobDataMap().put(SchedulerConstant.START_TIME, System.nanoTime());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jec, JobExecutionException jobException) {
        try {
            log.debug("Job was executed " + jec.getJobDetail().getKey().getName());
            insertRunningLog(jec, jobException);
            rescheduleIfExceptionIsNotNull(jobException, jec);
            clearJobDataMap(jec);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void rescheduleIfExceptionIsNotNull(JobExecutionException jobException, JobExecutionContext jec) throws SchedulerException {
        final JobDataMap jobDataMap = jec.getJobDetail().getJobDataMap();
        boolean retry = jobDataMap.getBoolean(SchedulerConstant.RETRY_FLAG);
        boolean onetime = jobDataMap.getBoolean(SchedulerConstant.MANUAL_EXECUTE);
        if (jobException != null && retry && !onetime) {
            int retryCount = jobDataMap.getInt(SchedulerConstant.RETRY_COUNT);
            int maxRetry = jobDataMap.getInt(SchedulerConstant.RETRY_MAX);
            int delay = jobDataMap.getInt(SchedulerConstant.RETRY_DELAY);
            jobDataMap.put(SchedulerConstant.RETRY_COUNT, ++retryCount);
            final String jobName = jec.getJobDetail().getKey().getName();
            final String group = jec.getJobDetail().getKey().getGroup();
            Trigger trigger;
            if (retryCount <= maxRetry) {
                log.info("retry : " + retryCount);
                trigger = newTrigger()
                        .forJob(jec.getJobDetail())
                        .withIdentity(jobName, group)
                        .withPriority(100)
                        .startAt(futureDate(delay, IntervalUnit.MINUTE))
                        .build();
            } else {
                log.info("job failed more than " + maxRetry + " times");
                trigger = rescheduleToOriginal(jobDataMap, jec, jobName, group);
                resetRetryCounter(jec);
            }
            jec.getScheduler().rescheduleJob(jec.getTrigger().getKey(), trigger);
        } else if (jobDataMap.getInt(SchedulerConstant.RETRY_COUNT) > 0 && retry && !onetime) {
            //If > 0 then this job is retried so we need to set original expression back
            log.info(jec.getJobDetail().getKey().getName() + " job has been reset expression to original");
            final Trigger originalTrigger = rescheduleToOriginal(jobDataMap, jec, listenerName, listenerName);
            resetRetryCounter(jec);
            jec.getScheduler().rescheduleJob(jec.getTrigger().getKey(), originalTrigger);
        }
    }

    private Trigger rescheduleToOriginal(final JobDataMap jobDataMap, JobExecutionContext jec, final String jobName, final String group) {
        Trigger trigger;
        final String originalExpression = jobDataMap.getString(SchedulerConstant.EXPRESSION_ORIGINAL);
        final int priority = jobDataMap.getInt(SchedulerConstant.PRIORITY);
        trigger = newTrigger()
                .forJob(jec.getJobDetail())
                .withIdentity(jobName, group)
                .withPriority(priority)
                .withSchedule(cronSchedule(originalExpression).inTimeZone(TimeZone.getTimeZone("GMT+7")))
                .build();
        return trigger;
    }

    private void resetRetryCounter(JobExecutionContext jec) {
        jec.getJobDetail().getJobDataMap().put(SchedulerConstant.RETRY_COUNT, 0);
    }

    private void insertRunningLog(JobExecutionContext jec, JobExecutionException jobException) {
        final Long jobId = jec.getJobDetail().getJobDataMap().getLong(SchedulerConstant.SCHEDULER_ID);
        final Date startDate = (Date) jec.getJobDetail().getJobDataMap().get(SchedulerConstant.START_DATE);
        final SchedulerHistoryBean schedulerHistoryModelBean = new SchedulerHistoryBean();
        schedulerHistoryModelBean.setSchedulerId(jobId);
        schedulerHistoryModelBean.setCreatedBy(Long.valueOf(1));
        schedulerHistoryModelBean.setExecutedDate(startDate);
        schedulerHistoryModelBean.setResultCode(1);
        if (jobException != null) {
            final StringWriter sw = new StringWriter();
            jobException.printStackTrace(new PrintWriter(sw));
            schedulerHistoryModelBean.setResultCode(0);
            schedulerHistoryModelBean.setResultMessage(jobException.getMessage());
            schedulerHistoryModelBean.setErrorMessage(sw.toString());
        }
        schedulerService.insertSchedulerHistory(schedulerHistoryModelBean);
    }

    private void clearJobDataMap(JobExecutionContext jec) {
        jec.getJobDetail().getJobDataMap().remove(SchedulerConstant.WS_NAME);
        jec.getJobDetail().getJobDataMap().remove(SchedulerConstant.WS_REQ);
        jec.getJobDetail().getJobDataMap().remove(SchedulerConstant.WS_RES);
        jec.getJobDetail().getJobDataMap().remove(SchedulerConstant.MANUAL_EXECUTE);
    }

}
