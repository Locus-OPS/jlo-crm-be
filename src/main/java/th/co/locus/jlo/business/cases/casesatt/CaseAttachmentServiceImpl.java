/**
 * 
 */
package th.co.locus.jlo.business.cases.casesatt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.business.cases.casesatt.bean.CaseAttachmentModelBean;
import th.co.locus.jlo.business.cases.casesatt.bean.SearchCaseAttachmentModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

/**
 * @author Mr.BoonOom
 *
 */
@Service
public class CaseAttachmentServiceImpl extends BaseService implements CaseAttachmentService {
	@Autowired
	private FileService fileService;
	
	@Override
	public ServiceResult<Page<CaseAttachmentModelBean>> getCaseAttachmentListByCaseNumber(SearchCaseAttachmentModelBean bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("caseAtt.getCaseAttachmentListByCaseNumber", bean, pageRequest));
	}

	@Override
	public ServiceResult<CaseAttachmentModelBean> createCaseAttachment(CaseAttachmentModelBean bean,FileModelBean fileBean, MultipartFile file) throws IOException {
	 	
		 if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			bean.setAttachmentId(fileBean.getAttId());
		 }
		
		int result = commonDao.insert("caseAtt.createCaseAttachment", bean);
		if(result > 0) {
			return success(commonDao.selectOne("caseAtt.getCaseAttachmentByAttachmentNumber", bean));
		}
	 
		return fail();
		
	}

	@Override
	public ServiceResult<CaseAttachmentModelBean> updateCaseAttachment(CaseAttachmentModelBean bean,FileModelBean fileBean, MultipartFile file) throws IOException {
		
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			bean.setAttachmentId(fileBean.getAttId());
		 }
		
		int result = commonDao.update("caseAtt.updateCaseAttachment", bean);
		if (result > 0) {
			return success(commonDao.selectOne("caseAtt.getCaseAttachmentByAttachmentNumber", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteCaseAttachment(CaseAttachmentModelBean bean) {
		return success(commonDao.delete("caseAtt.deleteCaseAttachment", bean));
	}

}
