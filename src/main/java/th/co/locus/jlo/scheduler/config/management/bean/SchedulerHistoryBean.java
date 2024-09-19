package th.co.locus.jlo.scheduler.config.management.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Getter
@Setter
public class SchedulerHistoryBean extends BaseModelBean {

    private Long id;
    private Long schedulerId;
    private Integer resultCode;
    private String resultMessage;
    private String errorMessage;
    private Date executedDate;
    private String schedulerName; //100
    private String expression; //50
}
