package th.co.locus.jlo.kb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.kb.modelbean.KbDetailInfoModelBean;
import th.co.locus.jlo.kb.modelbean.KbDocumentModelBean;
import th.co.locus.jlo.kb.modelbean.KbKeywordModelBean;
import th.co.locus.jlo.kb.modelbean.KbModelBean;
import th.co.locus.jlo.kb.modelbean.KbTreeModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKbFileSequenceModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKbFolderSequenceModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKeywordModelBean;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@RestController
@RequestMapping("api/kb")
public class KbController extends BaseController {
	
	@Value("${attachment.path.kb}")
	private String kbPath;
	
	@Autowired
	private KbService kbService;
	
	@Autowired
	private FileService fileService;
	
	@PostMapping(value = "/getKbTreeList", produces = "application/json")
	public ApiResponse<List<KbTreeModelBean>> getKbTreeList(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<KbTreeModelBean>> serviceResult = kbService.getKbTreeList(request.getData(), getBuId());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/getKbTreeFolderList", produces = "application/json")
	public ApiResponse<List<KbTreeModelBean>> getKbTreeFolderList(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<KbTreeModelBean>> serviceResult = kbService.getKbTreeList(request.getData(), getBuId(), 1);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/saveKbDetail", produces = "application/json")
	public ApiResponse<KbModelBean> saveKbDetail(@RequestBody ApiRequest<KbModelBean> request) {
		KbModelBean kbModelBean = request.getData();
		kbModelBean.setCreatedBy(getUserId());
		kbModelBean.setUpdatedBy(getUserId());
		kbModelBean.setBuId(getBuId());
		ServiceResult<KbModelBean> serviceResult;
		if (kbModelBean.getContentId() != null) {
			serviceResult = kbService.updateKbDetail(kbModelBean);
		} else {
			ServiceResult<Integer> findMaxSequenceServiceResult = kbService
					.findMaxSequenceContent(kbModelBean.getCatId());
			Integer currentSeq = findMaxSequenceServiceResult.getResult();
			Integer newSeq = currentSeq + 1;
			kbModelBean.setSeq(newSeq);
			serviceResult = kbService.createKbDetail(kbModelBean);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@PostMapping(value = "/saveKbDetailInfo", produces = "application/json")
	public ApiResponse<KbDetailInfoModelBean> saveKbDetailInfo(@RequestBody ApiRequest<KbDetailInfoModelBean> request) {
		KbDetailInfoModelBean kbDetailInfoModelBean = request.getData();
		kbDetailInfoModelBean.setCreatedBy(getUserId());
		kbDetailInfoModelBean.setUpdatedBy(getUserId());
		kbDetailInfoModelBean.setBuId(getBuId());
		CommonUtil.nullifyObject(kbDetailInfoModelBean);
		ServiceResult<KbDetailInfoModelBean> serviceResult = kbService.updateKbDetailInfo(kbDetailInfoModelBean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@PostMapping(value = "/findKbDetailById", produces = "application/json")
	public ApiResponse<KbModelBean> findKbDetailById(@RequestBody ApiRequest<Long> request) {
		Long contentId = request.getData();
		if (contentId == null) {
			KbModelBean kbModelBean = new KbModelBean();
			return ApiResponse.success(kbModelBean);
		}
		ServiceResult<KbModelBean> serviceResult = kbService.findKbDetailById(contentId);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@PostMapping(value = "/findKbDetailInfoById", produces = "application/json")
	public ApiResponse<KbDetailInfoModelBean> findKbDetailInfoById(@RequestBody ApiRequest<Long> request) {
		ServiceResult<KbDetailInfoModelBean> serviceResult = kbService.findKbDetailInfoById(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@PostMapping(value = "/getKbKeywordList", produces = "application/json")
	public ApiResponse<List<KbKeywordModelBean>> getKbKeywordList(@RequestBody ApiRequest<Long> request) {
		Long contentId = request.getData();
		if (contentId == null) {
			return ApiResponse.success(new ArrayList<>());
		}
		ServiceResult<List<KbKeywordModelBean>> serviceResult = kbService.getKbKeywordList(contentId);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/updateKeywordByContentId", produces = "application/json")
	public ApiResponse<Boolean> updateKeywordByContentId(@RequestBody ApiRequest<UpdateKeywordModelBean> request) {
		ServiceResult<Boolean> serviceResult = kbService.updateKeywordByContentId(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/getKbDocumentList", produces = "application/json")
	public ApiResponse<List<KbDocumentModelBean>> getKbDocumentList(@RequestBody ApiRequest<Long> request) {
		Long contentId = request.getData();
		if (contentId == null) {
			return ApiResponse.success(new ArrayList<>());
		}
		ServiceResult<List<KbDocumentModelBean>> serviceResult = kbService.findKbDocumentByContentId(contentId);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@PostMapping(value = "/getKbMainDocument", produces = "application/json")
	public ApiResponse<KbDocumentModelBean> getKbMainDocument(@RequestBody ApiRequest<Long> request) {

		Long contentId = request.getData();

		ServiceResult<KbDocumentModelBean> serviceResult = kbService.getKbMainDocument(contentId, "Y");
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@PostMapping(value = "/saveKbDocument")
	public ApiResponse<KbDocumentModelBean> saveKbDocument(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "contentAttId", required = false) String contentAttId,
			@RequestParam(value = "contentId", required = true) String contentId, @RequestParam("attId") String attId,
			@RequestParam("title") String title, @RequestParam("descp") String descp,
			@RequestParam("mainFlag") String mainFlag) throws IOException {

		KbDocumentModelBean kbDoc = new KbDocumentModelBean();
		kbDoc.setContentAttId(StringUtils.isNotEmpty(contentAttId) ? Long.valueOf(contentAttId) : null);
		kbDoc.setAttId(StringUtils.isNotEmpty(attId) ? Long.valueOf(attId) : null);
		kbDoc.setContentId(Long.valueOf(contentId));
		kbDoc.setTitle(title);
		kbDoc.setDescp(descp);
		kbDoc.setMainFlag(mainFlag);
		kbDoc.setCreatedBy(getUserId());
		kbDoc.setUpdatedBy(getUserId());

		CommonUtil.nullifyObject(kbDoc);

		FileModelBean fileBean = null;
		if (file != null) {
			fileBean = new FileModelBean();
			fileBean.setFilePath(kbPath + File.separator + contentId);
			fileBean.setFileExtension(CommonUtil.getFileExtension(file));
			fileBean.setFileName(file.getOriginalFilename());
			fileBean.setFileSize(file.getSize());
			fileBean.setCreatedBy(getUserId());
			fileBean.setUpdatedBy(getUserId());
		}

		if (kbDoc.getContentAttId() == null) {
			return ApiResponse.success(kbService.createKbDocument(kbDoc, fileBean, file).getResult());
		} else {
			return ApiResponse.success(kbService.updateKbDocument(kbDoc, fileBean, file).getResult());
		}
	}

	@PostMapping(value = "/deleteKbDocumentById", produces = "application/json")
	public ApiResponse<Boolean> deleteKbDocumentById(@RequestBody ApiRequest<Long> request) {
		ServiceResult<Boolean> serviceResult = kbService.deleteKbDocumentById(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@GetMapping(value = "/doc")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(@RequestParam("filePath") String filePath) {
		Resource file = fileService.loadFile(filePath);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping(value = "/saveKBFolder", produces = "application/json")
	public ApiResponse<KbTreeModelBean> saveKBFolder(@RequestBody ApiRequest<KbTreeModelBean> request) {
		KbTreeModelBean kbTreeModelBean = request.getData();
		kbTreeModelBean.setCreatedBy(getUserId());
		kbTreeModelBean.setUpdatedBy(getUserId());
		kbTreeModelBean.setBuId(getBuId());
		ServiceResult<KbTreeModelBean> serviceResult;
		if (request.getData().getId() == null) {
			Long parentId = Long.parseLong(kbTreeModelBean.getParentId());
			ServiceResult<Integer> findMaxSequenceServiceResult = kbService
					.findMaxSequenceContentFolder(parentId);
			Integer currentSeq = findMaxSequenceServiceResult.getResult();
			Integer newSeq = currentSeq + 1;
			kbTreeModelBean.setSeq(newSeq);
			serviceResult = kbService.createKbFolder(kbTreeModelBean);
		} else {
			serviceResult = kbService.updateKbFolder(kbTreeModelBean);
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@PostMapping(value = "/deleteKbFolderById", produces = "application/json")
	public ApiResponse<Boolean> deleteKbFolderById(@RequestBody ApiRequest<Long> request) {
		ServiceResult<Boolean> serviceResult = kbService.deleteKbFolderById(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@PostMapping(value = "/updateKbFolderSequenceById", produces = "application/json")
	public ApiResponse<Boolean> updateKbFolderSequenceById(@RequestBody ApiRequest<UpdateKbFolderSequenceModelBean> request) {
		ServiceResult<Boolean> serviceResult = kbService.updateKbFolderSequence(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/deleteKbFileById", produces = "application/json")
	public ApiResponse<Boolean> deleteKbFileById(@RequestBody ApiRequest<Long> request) {
		ServiceResult<Boolean> serviceResult = kbService.deleteKbFileById(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	@PostMapping(value = "/updateKbFileSequenceById", produces = "application/json")
	public ApiResponse<Boolean> updateKbFileSequenceById(@RequestBody ApiRequest<UpdateKbFileSequenceModelBean> request) {
		ServiceResult<Boolean> serviceResult = kbService.updateKbFileSequence(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
}
