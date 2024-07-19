/**
 * 
 */
package th.co.locus.jlo.system.email.emaillog;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.email.emaillog.bean.EmailLogBean;
import th.co.locus.jlo.system.email.emaillog.bean.SearchEmailLogBean;

/**
 * @author apichat
 *
 */
public interface EmailLogService {
	public ServiceResult<Page<EmailLogBean>> getEmailLogList(SearchEmailLogBean bean, PageRequest pageRequest);

	public ServiceResult<EmailLogBean> createEmailLog(EmailLogBean bean);

	public ServiceResult<EmailLogBean> updateEmailLog(EmailLogBean bean);
	
	public ServiceResult<EmailLogBean> findEmailById(EmailLogBean bean);
}
