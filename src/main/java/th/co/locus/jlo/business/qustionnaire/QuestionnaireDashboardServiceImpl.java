package th.co.locus.jlo.business.qustionnaire;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardGenderGroupModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardValueModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
public class QuestionnaireDashboardServiceImpl extends BaseService  implements QuestionnaireDashboardService {

	@Override
	public ServiceResult<QuestionnaireMainDashboardModelBean> getMainDashboad(Long headerId) {
		try {
			QuestionnaireMainDashboardModelBean mainDashboard=new QuestionnaireMainDashboardModelBean();
			List<QuestionnaireDashboardValueModelBean> genderList = new ArrayList<QuestionnaireDashboardValueModelBean>();
			QuestionnaireDashboardGenderGroupModelBean genderGroup=commonDao.selectOne("questionnaire-dashboard.getQuestionnaireGenderGroup", Map.of("headerId",headerId));
			if(genderGroup!=null) {
				mainDashboard.setTotalRespondent(genderGroup.getTotalRespondent());
				genderList.add(new QuestionnaireDashboardValueModelBean("Male", genderGroup.getTotalMale().toString()));
				genderList.add(new QuestionnaireDashboardValueModelBean("Female", genderGroup.getTotalFemale().toString()));
				genderList.add(new QuestionnaireDashboardValueModelBean("Others", genderGroup.getTotalOthers().toString()));
				mainDashboard.setGenderGroup(genderList);
			}
			List<QuestionnaireDashboardValueModelBean> ageGroupList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireAgeGroupList", Map.of("headerId",headerId));
			if(ageGroupList.size()>0) {
				mainDashboard.setAgeGroup(ageGroupList);
			}
			
			return success(mainDashboard);
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<Page<QuestionnaireRepondentsModelBean>> getRespondent(Long headerId,PageRequest page) {
		try {
			Page<QuestionnaireRepondentsModelBean> respondentList=commonDao.selectPage("questionnaire-dashboard.getQuestionnaireRespondentList", headerId, page);
			return success(respondentList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

}
