package th.co.locus.jlo.kb.kbfavorite;


import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.kb.kbfavorite.bean.KBFavoriteModelBean;
@Log4j2
@Service
public class KBFavoriteServiceImpl extends BaseService implements KBFavoriteService{

	@Override
	public ServiceResult<Page<KBFavoriteModelBean>> getKBFavoriteList(KBFavoriteModelBean bean,PageRequest page) {
		try {
			return null;
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return null;
		}
		
	}

	@Override
	public ServiceResult<KBFavoriteModelBean> createKBFavorite(KBFavoriteModelBean bean) {
		try {

			ServiceResult<KBFavoriteModelBean> kbFavorite=this.getKBFavorite(bean);
			if(kbFavorite.getResult()==null) {
				commonDao.insert("kbfavorite.createKBFavorite",bean);
			}else {
				commonDao.update("kbfavorite.deleteKBFavorite", bean);
			}
			return this.getKBFavorite(bean);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<KBFavoriteModelBean> getKBFavorite(KBFavoriteModelBean bean) {
		try {
			KBFavoriteModelBean kbFavorite=commonDao.selectOne("kbfavorite.getKBFavorite", bean);
			return success(kbFavorite);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

}
