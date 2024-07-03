package th.co.locus.jlo.system.internationalization;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.internationalization.bean.InternationalizationModelBean;
import th.co.locus.jlo.system.internationalization.bean.SaveInternationalizationModelBean;
import th.co.locus.jlo.system.internationalization.bean.SearchInternationalizationModelBean;

public interface InternationalizationService {

    public ServiceResult<Page<InternationalizationModelBean>> getInternationalizationList(SearchInternationalizationModelBean bean, PageRequest pageRequest);

    public ServiceResult<Integer> saveInternationalization(SaveInternationalizationModelBean bean);

    public ServiceResult<Integer> deleteInternationalization(InternationalizationModelBean bean);
    
    public ServiceResult<List<InternationalizationModelBean>> getInternationalizationByMsgCode(SearchInternationalizationModelBean bean);
    
    public ServiceResult<List<InternationalizationModelBean>> getTranslation(String language);
}
