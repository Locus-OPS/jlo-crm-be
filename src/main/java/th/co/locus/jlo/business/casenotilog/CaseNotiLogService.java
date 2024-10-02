package th.co.locus.jlo.business.casenotilog;

import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogHeaderModelbean;
import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogModelbean;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface CaseNotiLogService {
	ServiceResult<CaseNotificationLogModelbean> createCaseNotiLog(CaseNotificationLogModelbean bean);
	ServiceResult<CaseNotificationLogHeaderModelbean> getCaseNotiLogList(String userId);
	ServiceResult<CaseNotificationLogModelbean> updateReadStatusCaseNoti(CaseNotificationLogModelbean bean);
}
