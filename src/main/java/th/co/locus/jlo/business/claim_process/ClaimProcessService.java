package th.co.locus.jlo.business.claim_process;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.business.claim_process.bean.ClaimProcessCriteriaModelBean;
import th.co.locus.jlo.business.claim_process.bean.ClaimProcessModelBean;
import th.co.locus.jlo.business.claim_process.bean.DataExtractionModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

import java.io.IOException;

public interface ClaimProcessService {
	
	public ServiceResult<Page<ClaimProcessModelBean>> searchDataExtraction(ClaimProcessCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ClaimProcessModelBean> createPrompt(DataExtractionModelBean bean);
	public ServiceResult<ClaimProcessModelBean> updatePrompt(DataExtractionModelBean bean);
	public ServiceResult<ClaimProcessModelBean> analyzeFile(MultipartFile file, String promptCode);

	public ServiceResult<ClaimProcessModelBean> callGeminiAPI(String promptCode, MultipartFile imageFile);
}
