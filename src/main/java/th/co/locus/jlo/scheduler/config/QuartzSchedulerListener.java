package th.co.locus.jlo.scheduler.config;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuartzSchedulerListener implements SchedulerListener {

    @Override
    public void jobScheduled(Trigger trigger) {
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
    }

    @Override
    public void triggersPaused(String triggerGroup) {
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
    }

    @Override
    public void triggersResumed(String triggerGroup) {
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
    }

    @Override
    public void jobPaused(JobKey jobKey) {
    }

    @Override
    public void jobsPaused(String jobGroup) {
    }

    @Override
    public void jobResumed(JobKey jobKey) {
    }

    @Override
    public void jobsResumed(String jobGroup) {
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
    }

    @Override
    public void schedulerInStandbyMode() {
    }

    @Override
    public void schedulerStarted() {
    }

    @Override
    public void schedulerStarting() {
        log.info("Quartz Scheduler is starting");
    }

    @Override
    public void schedulerShutdown() {
        log.info("Quartz Scheduler was shutdown.");
    }

    @Override
    public void schedulerShuttingdown() {
        log.info("Quartz Scheduler is shuttingdown...");
    }

    @Override
    public void schedulingDataCleared() {
    }

}
