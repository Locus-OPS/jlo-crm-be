package th.co.locus.jlo.scheduler.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.UnableToInterruptJobException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class AutoAssignCaseEmailInbound extends BaseQuartzJob implements InterruptableJob {


//    private SchedulerService schedulerService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
//        schedulerService = applicationContext.getBean(SchedulerService.class);
    }

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            log.info("executeDemoJob");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JobExecutionException jee = new JobExecutionException(e);
            throw jee;
        } finally {
            log.info("end");
        }
    }

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

}
