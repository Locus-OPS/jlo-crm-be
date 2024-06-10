package th.co.locus.jlo.kb;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.kb.modelbean.KbDetailInfoModelBean;
import th.co.locus.jlo.kb.modelbean.KbDocumentModelBean;
import th.co.locus.jlo.kb.modelbean.KbKeywordModelBean;
import th.co.locus.jlo.kb.modelbean.KbModelBean;
import th.co.locus.jlo.kb.modelbean.KbTreeModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKbFileSequenceModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKbFolderSequenceModelBean;
import th.co.locus.jlo.kb.modelbean.UpdateKeywordModelBean;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;

public interface KbService {

	public ServiceResult<List<KbTreeModelBean>> getKbTreeList(String type, Integer buId);
	ServiceResult<List<KbTreeModelBean>> getKbTreeList(String type, Integer buId, Integer isFolder);
	public ServiceResult<KbModelBean> createKbDetail(KbModelBean bean);
	public ServiceResult<KbModelBean> updateKbDetail(KbModelBean bean);
	public ServiceResult<KbDetailInfoModelBean> updateKbDetailInfo(KbDetailInfoModelBean bean);
	public ServiceResult<KbModelBean> findKbDetailById(Long contentId);
	public ServiceResult<KbDetailInfoModelBean> findKbDetailInfoById(Long contentId);
	public ServiceResult<List<KbKeywordModelBean>> getKbKeywordList(Long contentId);
	public ServiceResult<Boolean> updateKeywordByContentId(UpdateKeywordModelBean bean);
	public ServiceResult<List<KbDocumentModelBean>> findKbDocumentByContentId(Long contentId);
	public ServiceResult<KbDocumentModelBean> getKbMainDocument(Long contentId, String mainFlag);
	public ServiceResult<KbDocumentModelBean> createKbDocument(KbDocumentModelBean bean, FileModelBean fileBean, MultipartFile file) throws IOException;
	public ServiceResult<KbDocumentModelBean> updateKbDocument(KbDocumentModelBean bean, FileModelBean fileBean, MultipartFile file) throws IOException;
	public ServiceResult<Boolean> deleteKbDocumentById(Long contentAttId);
	public ServiceResult<KbTreeModelBean> createKbFolder(KbTreeModelBean bean);
	public ServiceResult<KbTreeModelBean> updateKbFolder(KbTreeModelBean bean);
	public ServiceResult<Boolean> deleteKbFolderById(Long catId);
	public ServiceResult<Boolean> updateKbFolderSequence(UpdateKbFolderSequenceModelBean bean);
	public ServiceResult<Boolean> deleteKbFileById(Long contentId);
	public ServiceResult<Boolean> updateKbFileSequence(UpdateKbFileSequenceModelBean bean);
	public ServiceResult<Integer> findMaxSequenceContent(Long catId);
	public ServiceResult<Integer> findMaxSequenceContentFolder(Long parentId);
}
