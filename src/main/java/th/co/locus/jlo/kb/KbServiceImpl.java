package th.co.locus.jlo.kb;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.kb.modelbean.KbDetailInfoModelBean;
import th.co.locus.jlo.kb.modelbean.KbDocumentModelBean;
import th.co.locus.jlo.kb.modelbean.KbKeywordModelBean;
import th.co.locus.jlo.kb.modelbean.KbModelBean;
import th.co.locus.jlo.kb.modelbean.KbTreeModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKbFileSequenceModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKbFolderSequenceModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKeywordModelBean;
import th.co.locus.jlo.util.file.FileService;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;

@Service
public class KbServiceImpl extends BaseService implements KbService {
	
	@Autowired
	private FileService fileService;

	@Override
	public ServiceResult<List<KbTreeModelBean>> getKbTreeList(String type, Integer buId) {
		return success(commonDao.selectList("kb.getKbTreeList", Map.of("type", type, "buId", buId)));
	}

	@Override
	public ServiceResult<List<KbTreeModelBean>> getKbTreeList(String type, Integer buId, Integer isFolder) {
		return success(commonDao.selectList("kb.getKbTreeList", Map.of("type", type, "buId", buId, "isFolder", isFolder)));
	}

	@Override
	public ServiceResult<KbModelBean> createKbDetail(KbModelBean bean) {
		int result = commonDao.insert("kb.createKbDetail", bean);
		if (result > 0) {
			return success(commonDao.selectOne("kb.findKbDetailById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<KbModelBean> updateKbDetail(KbModelBean bean) {
		int result = commonDao.insert("kb.updateKbDetail", bean);
		if (result > 0) {
			return success(commonDao.selectOne("kb.findKbDetailById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<KbDetailInfoModelBean> updateKbDetailInfo(KbDetailInfoModelBean bean) {
		int result = commonDao.insert("kb.updateKbDetailInfo", bean);
		if (result > 0) {
			return success(commonDao.selectOne("kb.findKbDetailInfoById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<KbModelBean> findKbDetailById(Long contentId) {
		return success(commonDao.selectOne("kb.findKbDetailById", Map.of("contentId", contentId)));
	}

	@Override
	public ServiceResult<KbDetailInfoModelBean> findKbDetailInfoById(Long contentId) {
		return success(commonDao.selectOne("kb.findKbDetailInfoById", Map.of("contentId", contentId)));
	}

	@Override
	public ServiceResult<List<KbKeywordModelBean>> getKbKeywordList(Long contentId) {
		return success(commonDao.selectList("kb.findKeywordByContentId", Map.of("contentId", contentId)));
	}

	@Override
	public ServiceResult<Boolean> updateKeywordByContentId(UpdateKeywordModelBean bean) {
		commonDao.delete("kb.deleteKeywordByContentId", Map.of("contentId", bean.getContentId()));
		int rowCount = commonDao.update("kb.createKeyword", Map.of("keywordList", bean.getKeywordList(), "createdBy", CommonUtil.getUserId()));
		if (rowCount > 0) {
			return success(Boolean.TRUE);
		} else {
			return fail();
		}
	}

	@Override
	public ServiceResult<List<KbDocumentModelBean>> findKbDocumentByContentId(Long contentId) {
		return success(commonDao.selectList("kb.findDocumentByContentId", Map.of("contentId", contentId)));
	}

	@Override
	public ServiceResult<KbDocumentModelBean> getKbMainDocument(Long contentId, String mainFlag) {
		return success(commonDao.selectOne("kb.findDocumentByContentId",
				Map.of("contentId", contentId, "mainFlag", mainFlag)));
	}

	@Override
	@Transactional
	public ServiceResult<KbDocumentModelBean> createKbDocument(KbDocumentModelBean bean, FileModelBean fileBean, MultipartFile file) throws IOException {
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			bean.setAttId(fileBean.getAttId());
		}
		if ("Y".equals(bean.getMainFlag())) {
			commonDao.update("kb.updateKbDocumentMainFlagNoByContentId", Map.of("contentId", bean.getContentId()));
		}
		commonDao.insert("kb.createKbDocument", bean);
		
		return success(commonDao.selectOne("kb.findKbDocumentById", bean));
	}

	@Override
	@Transactional
	public ServiceResult<KbDocumentModelBean> updateKbDocument(KbDocumentModelBean bean, FileModelBean fileBean, MultipartFile file) throws IOException {
		if (file != null) {
			fileBean = fileService.createAttachment(fileBean).getResult(); 
			fileService.saveFile(file, fileBean.getFilePath(), fileBean.getFileName());
			bean.setAttId(fileBean.getAttId());
		}
		if ("Y".equals(bean.getMainFlag())) {
			commonDao.update("kb.updateKbDocumentMainFlagNoByContentId", Map.of("contentId", bean.getContentId()));
		}
		commonDao.update("kb.updateKbDocument", bean);
		
		return success(commonDao.selectOne("kb.findKbDocumentById", bean));		
	}

	@Override
	public ServiceResult<Boolean> deleteKbDocumentById(Long contentAttId) {
		int rows = commonDao.delete("kb.deleteKbDocumentById", Map.of("contentAttId", contentAttId));
		if (rows > 0) {
			return success(Boolean.TRUE);
		}
		return fail();
	}

	@Override
	public ServiceResult<KbTreeModelBean> createKbFolder(KbTreeModelBean bean) {
		int result = commonDao.insert("kb.createKbFolder", bean);
		if (result > 0) {
			return success(commonDao.selectOne("kb.findKbFolderById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<KbTreeModelBean> updateKbFolder(KbTreeModelBean bean) {
		int result = commonDao.insert("kb.updateKbFolder", bean);
		if (result > 0) {
			return success(commonDao.selectOne("kb.findKbFolderById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Boolean> deleteKbFolderById(Long catId) {
		int rows = commonDao.delete("kb.deleteKbFolderById", Map.of("catId", catId));
		if (rows > 0) {
			return success(Boolean.TRUE);
		}
		return fail();
	}

	@Override
	public ServiceResult<Boolean> updateKbFolderSequence(UpdateKbFolderSequenceModelBean bean) {
		commonDao.update("kb.updateKbFolderSeq", bean);
		return success(true);
	}

	@Override
	public ServiceResult<Boolean> deleteKbFileById(Long contentId) {
		int rows = commonDao.delete("kb.deleteKbFileById", Map.of("contentId", contentId));
		if (rows > 0) {
			return success(Boolean.TRUE);
		}
		return fail();
	}

	@Override
	public ServiceResult<Boolean> updateKbFileSequence(UpdateKbFileSequenceModelBean bean) {
		commonDao.update("kb.updateKbFileSeq", bean);
		return success(true);
	}

	@Override
	public ServiceResult<Integer> findMaxSequenceContent(Long catId) {
		return success(commonDao.selectOne("kb.findMaxSequenceContent", Map.of("catId", catId)));
	}

	@Override
	public ServiceResult<Integer> findMaxSequenceContentFolder(Long parentId) {
		return success(commonDao.selectOne("kb.findMaxSequenceContentFolder", Map.of("parentId", parentId)));
	}
}
