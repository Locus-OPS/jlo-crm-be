/**
 * 
 */
package th.co.locus.jlo.business.loyalty.casesatt;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.business.loyalty.casesatt.bean.CaseAttachmentModelBean;
import th.co.locus.jlo.business.loyalty.casesatt.bean.SearchCaseAttachmentModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;

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
