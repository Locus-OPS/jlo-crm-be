package th.co.locus.jlo.business.qustionnaire;

import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface QuestionnaireDashboardService {
	ServiceResult<QuestionnaireMainDashboardModelBean> getMainDashboad(Long headerId);
	ServiceResult<Page<QuestionnaireRepondentsModelBean>> getRespondent(Long headerId,PageRequest page);
}
