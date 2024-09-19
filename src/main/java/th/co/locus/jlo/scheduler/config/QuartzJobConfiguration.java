package th.co.locus.jlo.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.scheduler.config.management.SchedulerService;
import th.co.locus.jlo.scheduler.config.management.bean.SchedulerBean;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import jakarta.servlet.ServletContext;

//import javax.annotation.PreDestroy;
//import javax.servlet.ServletContext;

import java.util.List;

@Slf4j
@Component
public class QuartzJobConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Value("${scheduler.enable}")
    private Boolean enable;

    private static boolean initialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        if (enable) {
            if (schedulerService.runOnThisContext()) {
                if (e.getApplicationContext().equals(appContext)) {
                    if (!initialized) {
                        initScheduler();
                        initialized = true;
                    }
                }
            } else {
                log.info("Scheduler Job not running on this Context (" + servletContext.getContextPath() + ")");
            }
        } else {
            log.info("Scheduler is disabled. To enable go to Application.properties -> scheduler.enable=true");
        }
    }

    public void initScheduler() {
        log.debug("2.Initialize Quartz Scheduler");
        final ServiceResult<List<SchedulerBean>> serviceResult = schedulerService.findAllScheduler();
        if (serviceResult.isSuccess()) {
            final List<SchedulerBean> schedulerBean = serviceResult.getResult();
            schedulerBean.forEach((s) -> {
                try {
                    schedulerService.scheduleJob(s);
                } catch (ClassNotFoundException | SchedulerException ex) {
                    log.error(ex.getMessage(), ex);
                }
            });
        } else {
            log.error(serviceResult.getThrowable().getMessage(), serviceResult.getThrowable());
        }
    }

    @PreDestroy
    public void shutdownScheduler() throws SchedulerException {
        try {
            schedulerFactoryBean.getScheduler().shutdown(false);
            schedulerFactoryBean.destroy();
        } catch (SchedulerException ex) {
            log.info(ex.getMessage(), ex);
        }
    }
}
