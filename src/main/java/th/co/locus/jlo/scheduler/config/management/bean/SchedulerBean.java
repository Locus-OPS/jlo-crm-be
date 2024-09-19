package th.co.locus.jlo.scheduler.config.management.bean;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Getter
@Setter
public class SchedulerBean extends BaseModelBean {
    private Long id;
    private String schedulerName; //100
    private String group;
    private String schedulerType;
    private String expression; //50
    private String classpath; //300
    private String runonInstance;
    private String runonContextRoot;
    private String useYn;
    private Integer priority;
    private Date fireTime;
    private Date nextFireTime;
    private String status;
    private Integer seq;
}
