package th.co.locus.jlo.kb.kbfavorite;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.kb.kbfavorite.bean.KBFavoriteModelBean;

public interface KBFavoriteService {
	ServiceResult<Page<KBFavoriteModelBean>> getKBFavoriteList(KBFavoriteModelBean bean,PageRequest page);
	ServiceResult<KBFavoriteModelBean> getKBFavorite(KBFavoriteModelBean bean);
	ServiceResult<KBFavoriteModelBean> createKBFavorite(KBFavoriteModelBean bean);
}
