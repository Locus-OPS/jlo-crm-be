package th.co.locus.jlo.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.UnableToInterruptJobException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.mail.inbound.InboundReceiveMailService;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class AutoAssignCaseEmailInboundJob extends BaseQuartzJob implements InterruptableJob {


    private InboundReceiveMailService inboundReceiveMailService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
         inboundReceiveMailService = applicationContext.getBean(InboundReceiveMailService.class);
    }

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            log.info("executeAutoAssignCaseEmailInboundJob");
            inboundReceiveMailService.assignEmailToCase();
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
		 log.info("interrupting assignEmailToCase");
		
	}

}
