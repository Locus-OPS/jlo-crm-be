package th.co.locus.jlo.business.casenotilog.bean;

import java.util.List;

import lombok.Data;

@Data
public class CaseNotificationLogHeaderModelbean {
	private Long total;
	private String userId;
	private List<CaseNotificationLogModelbean> caseNotilogList;
	private List<CaseNotificationLogModelbean> caseActNotilogList;
}
