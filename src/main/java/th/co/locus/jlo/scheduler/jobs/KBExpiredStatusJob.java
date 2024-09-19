package th.co.locus.jlo.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.kb.KbService;

import org.springframework.context.ApplicationContext;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class KBExpiredStatusJob extends BaseQuartzJob implements InterruptableJob{
	private KbService kbService;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		kbService = applicationContext.getBean(KbService.class);
	}
	
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            log.info("execute KB Expired Status Job");
            kbService.setExpiredKB();
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
        log.info("interrupting SAPCheckFIStatusJob");
    }

}
