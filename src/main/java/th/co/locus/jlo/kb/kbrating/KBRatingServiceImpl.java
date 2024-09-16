package th.co.locus.jlo.kb.kbrating;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.kb.kbrating.bean.KBRatingModelBean;

@Service
@Log4j2
public class KBRatingServiceImpl extends BaseService implements KBRatingService {

	@Override
	public ServiceResult<KBRatingModelBean> createKBRating(KBRatingModelBean bean) {
		try {
			List<KBRatingModelBean> kbRatingList=commonDao.selectList("kbrating.getKBRatingByUser", bean);
			if(kbRatingList.size()>0) {
				commonDao.update("kbrating.updateKBRating", bean);
			}else {
				commonDao.insert("kbrating.createKBRating", bean);
			}
			return this.getKBRating(bean);
			//return success(commonDao.selectOne("kbrating.getKBRating", bean));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<KBRatingModelBean> getKBRating(KBRatingModelBean bean) {
		try {
			KBRatingModelBean rating=commonDao.selectOne("kbrating.getKBRating", bean);
			if(rating==null) {
				KBRatingModelBean emptRating=new KBRatingModelBean();
				emptRating.setContentId(bean.getContentId());
				emptRating.setUserId(bean.getUserId());
				emptRating.setCreatedBy(0L);
				emptRating.setRating(new BigDecimal(0));
				emptRating.setAvgRating(new BigDecimal(0));
				emptRating.setTotalReviewer(0L);
				return success(emptRating);
			}else {
				return success(rating);
			}
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}



}
