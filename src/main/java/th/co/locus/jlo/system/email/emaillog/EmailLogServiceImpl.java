/**
 * 
 */
package th.co.locus.jlo.system.email.emaillog;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.email.emaillog.bean.EmailLogBean;
import th.co.locus.jlo.system.email.emaillog.bean.SearchEmailLogBean;

/**
 * @author apichat
 *
 */
@Service
public class EmailLogServiceImpl extends BaseService implements EmailLogService {

	@Override
	public ServiceResult<Page<EmailLogBean>> getEmailLogList(SearchEmailLogBean bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("emaillog.getEmailLogList", bean, pageRequest));
	}

	@Override
	public ServiceResult<EmailLogBean> createEmailLog(EmailLogBean bean) {
		int result = commonDao.update("emaillog.createEmailLog", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emaillog.findEmailById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<EmailLogBean> updateEmailLog(EmailLogBean bean) {
		int result = commonDao.update("emaillog.updateEmailLog", bean);
		if (result > 0) {
			return success(commonDao.selectOne("emaillog.findEmailById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<EmailLogBean> findEmailById(EmailLogBean bean) {
		return success(commonDao.selectOne("emaillog.findEmailById", bean));
	}

}
