package th.co.locus.jlo.scheduler.jobs;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class BaseQuartzJob extends QuartzJobBean {

    public abstract void setApplicationContext(ApplicationContext applicationContext);

}
