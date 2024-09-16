package th.co.locus.jlo.kb.kbrating;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.kb.kbrating.bean.KBRatingModelBean;

public interface KBRatingService {
	ServiceResult<KBRatingModelBean> createKBRating(KBRatingModelBean bean);
	ServiceResult<KBRatingModelBean> getKBRating(KBRatingModelBean bean);
}
