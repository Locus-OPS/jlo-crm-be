package th.co.locus.jlo.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Slf4j
@Configuration
public class QuartzConfiguration {


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        log.debug("1.Initialize Quartz SchedulerFactoryBean");
        final SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // Context key name must be declared as setter method in class that extend QuartzJobBean
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setSchedulerName("MakroCATScheduler");
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setExposeSchedulerInRepository(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        QuartzSchedulerListener[] quartzSchedulerListener = {new QuartzSchedulerListener()};
        schedulerFactoryBean.setSchedulerListeners(quartzSchedulerListener);
        return schedulerFactoryBean;
    }

}
