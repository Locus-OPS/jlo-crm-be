package th.co.locus.jlo.system.internationalization;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.internationalization.bean.InternationalizationModelBean;
import th.co.locus.jlo.system.internationalization.bean.SaveInternationalizationModelBean;
import th.co.locus.jlo.system.internationalization.bean.SearchInternationalizationModelBean;

@Service
public class InternationalizationServiceImpl extends BaseService implements InternationalizationService {

    @Override
    public ServiceResult<Page<InternationalizationModelBean>> getInternationalizationList(SearchInternationalizationModelBean bean, PageRequest pageRequest) {
        return success(commonDao.selectPage("internationalization.getInternationalizationList", bean, pageRequest));
    }

    @Override
    public ServiceResult<Integer> saveInternationalization(SaveInternationalizationModelBean bean) {
    	if (StringUtils.isNotEmpty(bean.getPreviousMsgCode())) {    		
    		commonDao.delete("internationalization.deleteInternationalization", Map.of("msgCode", bean.getPreviousMsgCode()));
    	}
    	commonDao.delete("internationalization.deleteInternationalization", bean);
        return success(commonDao.insert("internationalization.createInternationalization", bean));        
    }

    @Override
    public ServiceResult<Integer> deleteInternationalization(InternationalizationModelBean bean) {
        return success(commonDao.delete("internationalization.deleteInternationalization", bean));
    }

	@Override
	public ServiceResult<List<InternationalizationModelBean>> getInternationalizationByMsgCode(
			SearchInternationalizationModelBean bean) {
		return success(commonDao.selectList("internationalization.getInternationalizationByMsgCode", bean));
	}

	@Override
	public ServiceResult<List<InternationalizationModelBean>> getTranslation(String language) {
		return success(commonDao.selectList("internationalization.getTranslation", Map.of("language", language)));
	}
}
