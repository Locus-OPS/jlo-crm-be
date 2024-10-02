package th.co.locus.jlo.business.casenotilog.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseNotificationLogModelbean extends BaseModelBean  {
	private Long id;
	private String userId;
	private String caseNumber;
	private String description;
	private boolean isRead;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date readAt;
	private String activityNumber;
}
