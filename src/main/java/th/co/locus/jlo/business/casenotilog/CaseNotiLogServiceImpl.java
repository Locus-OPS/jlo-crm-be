package th.co.locus.jlo.business.casenotilog;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogHeaderModelbean;
import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogModelbean;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Service
@Log4j2
public class CaseNotiLogServiceImpl extends BaseService implements CaseNotiLogService {

	@Override
	public ServiceResult<CaseNotificationLogModelbean> createCaseNotiLog(CaseNotificationLogModelbean bean) {
		try {
			int caseNotiLog=commonDao.insert("caseNotiLog.createCaseNotiLog", bean);
			if(caseNotiLog>0) {
				return success(bean);
			}
			return fail("500","Can't create case notification log");
		}catch(Exception ex) {
			log.error("Error while creating case notification log: {}", ex.getMessage(), ex);
			String errorMessage = "Internal server error occurred while creating case notification log.";
	        return fail("500", errorMessage);
		}
		
	}

	@Override
	public ServiceResult<CaseNotificationLogHeaderModelbean> getCaseNotiLogList(String userId) {
		try {
			CaseNotificationLogHeaderModelbean header=new CaseNotificationLogHeaderModelbean();
			List<CaseNotificationLogModelbean> caseNotiList=commonDao.selectList("caseNotiLog.getCaseNotiList",Map.of("userId",userId));
			List<CaseNotificationLogModelbean> actNotiList=commonDao.selectList("caseNotiLog.getActivityNotiList",Map.of("userId",userId));
			header.setUserId(userId);
			Long total=(long)(caseNotiList.size()+actNotiList.size());
			header.setTotal(total);
			header.setCaseNotilogList(caseNotiList);
			header.setCaseActNotilogList(actNotiList);
			return success(header);
		}catch(Exception ex) {
			log.error("Error while creating case notification log: {}", ex.getMessage(), ex);
			String errorMessage = "Internal server error occurred while creating case notification.";
			return fail("500",errorMessage);
		}
		
	}

	@Override
	public ServiceResult<CaseNotificationLogModelbean> updateReadStatusCaseNoti(CaseNotificationLogModelbean bean) {
		try {
			int result=commonDao.update("caseNotiLog.updateReadStatus", bean);
			if(result>0) {
				return success(bean);
			}
			return fail("500","Unable to update data.");
		}catch(Exception ex) {
			log.error("Error while creating case notification log: {}", ex.getMessage(), ex);
			String errorMessage = "Internal server error occurred while updating case notification";
			return fail("500",errorMessage);
		}
	}



}
