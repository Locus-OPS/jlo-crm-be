/**
 * 
 */
package th.co.locus.jlo.business.cases.casesatt;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.business.cases.casesatt.bean.CaseAttachmentModelBean;
import th.co.locus.jlo.business.cases.casesatt.bean.SearchCaseAttachmentModelBean;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

/**
 * @author Mr.BoonOom
 *
 */
@RestController
@RequestMapping("api/caseatt")
public class CaseAttachmentController extends BaseController {

	@Value("${attachment.path.att}")
	private String attPath;

	@Autowired
	private CaseAttachmentService caseAttachmentService;

	@PostMapping(value = "/getCaseAttachmentListByCaseNumber", produces = "application/json")
	public ApiPageResponse<List<CaseAttachmentModelBean>> getCaseAttachmentListByCaseNumber(
			@RequestBody ApiPageRequest<SearchCaseAttachmentModelBean> request) {
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CaseAttachmentModelBean>> serviceResult = caseAttachmentService.getCaseAttachmentListByCaseNumber(request.getData(),
				pageRequest);

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@PostMapping(value = "/createCaseAttachment")
	public ApiResponse<CaseAttachmentModelBean> createCaseAttachment(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "caseNumber", required = false) String caseNumber,
			@RequestParam(value = "caseAttId", required = false) String caseAttId,
			@RequestParam(value = "attachmentId", required = false) Long attachmentId) {

		CaseAttachmentModelBean attModelBean = new CaseAttachmentModelBean();
		attModelBean.setCreatedBy(getUserId());
		attModelBean.setUpdatedBy(getUserId());
		attModelBean.setBuId(getBuId());
		attModelBean.setCaseNumber(caseNumber);
		attModelBean.setCaseAttId(!StringUtils.isEmpty(caseAttId) ? Long.valueOf(caseAttId) : null);

		FileModelBean fileBean = null;
		if (file != null) {
			fileBean = new FileModelBean();
			fileBean.setFilePath(attPath + File.separator + "caseAtt");
			fileBean.setFileExtension(CommonUtil.getFileExtension(file));
			fileBean.setFileName(file.getOriginalFilename());
			fileBean.setFileSize(file.getSize());
			fileBean.setCreatedBy(getUserId());
			fileBean.setUpdatedBy(getUserId());
		}

		try {
			if (StringUtils.isEmpty(caseAttId)) {
				return ApiResponse.success(caseAttachmentService.createCaseAttachment(attModelBean, fileBean, file).getResult());
			} else {

				return ApiResponse.success(caseAttachmentService.updateCaseAttachment(attModelBean, fileBean, file).getResult());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ApiResponse.fail();

	}

	@PostMapping(value = "/deleteCaseAttachment", produces = "application/json")
	public ApiResponse<Integer> deleteCaseAttachment(@RequestBody ApiRequest<CaseAttachmentModelBean> request) {
		return ApiResponse.success(caseAttachmentService.deleteCaseAttachment(request.getData()).getResult());
	}

}
