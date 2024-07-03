/**
 * 
 */
package th.co.locus.jlo.business.cases.casesatt;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.business.cases.casesatt.bean.CaseAttachmentModelBean;
import th.co.locus.jlo.business.cases.casesatt.bean.SearchCaseAttachmentModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

/**
 * @author Mr.BoonOom
 *
 */
public interface CaseAttachmentService {
	
	public ServiceResult<Page<CaseAttachmentModelBean>> getCaseAttachmentListByCaseNumber(SearchCaseAttachmentModelBean bean,
																						  PageRequest pageRequest);

	public ServiceResult<CaseAttachmentModelBean> createCaseAttachment(CaseAttachmentModelBean bean,FileModelBean fileBean, MultipartFile file) throws IOException ;

	public ServiceResult<CaseAttachmentModelBean> updateCaseAttachment(CaseAttachmentModelBean bean,FileModelBean fileBean, MultipartFile file) throws IOException ;

	public ServiceResult<Integer> deleteCaseAttachment(CaseAttachmentModelBean bean);

}
