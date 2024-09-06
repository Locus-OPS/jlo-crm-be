package th.co.locus.jlo.business.qustionnaire;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireMainDashboardModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireQuestionSummaryModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireRepondentsModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardGenderGroupModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireDashboardValueModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireHeaderModelBean;
import th.co.locus.jlo.business.qustionnaire.bean.QuestionnaireResponsesModelBean;
import th.co.locus.jlo.common.bean.ExcelModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.common.util.ExcelTemplateBuilder;
import th.co.locus.jlo.util.JloExcelTemplate;
import java.util.function.Consumer;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
	public ServiceResult<Page<QuestionnaireRepondentsModelBean>> getRespondent(QuestionnaireRepondentsModelBean bean,PageRequest page) {
		try {
			Page<QuestionnaireRepondentsModelBean> respondentList=commonDao.selectPage("questionnaire-dashboard.getQuestionnaireRespondentList", bean, page);
			return success(respondentList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<List<QuestionnaireDashboardValueModelBean>> getQuestionnaireSummaryText(QuestionnaireQuestionModelBean bean) {
		try {
			List<QuestionnaireDashboardValueModelBean> summaryTextList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryTextList", Map.of("headerId",bean.getHeaderId(),"questionId",bean.getId(),"isShortList","Y"));
			return success(summaryTextList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<QuestionnaireQuestionSummaryModelBean> getQuestionResponseSummary(Long headerId) {
		try {
			log.info("Header :: "+headerId.toString());
			QuestionnaireQuestionSummaryModelBean summaryList=new QuestionnaireQuestionSummaryModelBean(); 
			List<QuestionnaireQuestionModelBean> questionList=commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",headerId));

			for (QuestionnaireQuestionModelBean question : questionList) {

				if(question.getComponentType().equals("text") || question.getComponentType().equals("textarea")) {
					List<QuestionnaireDashboardValueModelBean> summaryTextList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryTextList", Map.of("headerId",headerId,"questionId",question.getId(),"isShortList","Y"));
					if(summaryTextList.size()>0) {
						question.setSummaryDetail(summaryTextList);
					}
				}
				else if(question.getComponentType().equals("date")) {
					List<QuestionnaireDashboardValueModelBean> summaryDateList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryDateList", Map.of("headerId",headerId,"questionId",question.getId(),"isShortList","Y"));
					if(summaryDateList.size()>0) {
						question.setSummaryDetail(summaryDateList);
					}
				}
				else if(question.getComponentType().equals("radio") || question.getComponentType().equals("select") || question.getComponentType().equals("checkbox")) {
					List<QuestionnaireDashboardValueModelBean> summaryDateList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryRadioSelectList", Map.of("headerId",headerId,"questionId",question.getId()));
					if(summaryDateList.size()>0) {
						question.setSummaryDetail(summaryDateList);
					}
				}
				
			}
			summaryList.setQuestion(questionList);
			return success(summaryList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}

	@Override
	public ServiceResult<QuestionnaireQuestionSummaryModelBean> getQuestionResponseDetail(Long headerId,Long respondentId) {
		try {
			QuestionnaireQuestionSummaryModelBean summaryList=new QuestionnaireQuestionSummaryModelBean(); 
			List<QuestionnaireQuestionModelBean> questionList=commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",headerId));
			for (QuestionnaireQuestionModelBean question : questionList) {

				if(question.getComponentType().equals("date")) {
					List<QuestionnaireDashboardValueModelBean> responseDateDetail=commonDao.selectList("questionnaire-dashboard.getQuestionnaireResponseDate", Map.of("headerId",headerId,"questionId",question.getId(),"respondentId",respondentId));
					question.setResponseDetail(responseDateDetail);
				}else {
					List<QuestionnaireDashboardValueModelBean> responseTextDetail=commonDao.selectList("questionnaire-dashboard.getQuestionnaireResponseText", Map.of("headerId",headerId,"questionId",question.getId(),"respondentId",respondentId));
					question.setResponseDetail(responseTextDetail);
				}
				
			}
			summaryList.setQuestion(questionList);
			return success(summaryList);
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
	}

	@Override
	public ServiceResult<ByteArrayOutputStream> exportQuestionnaireSummary(QuestionnaireQuestionModelBean bean) {
		try {
			
			List<QuestionnaireDashboardValueModelBean> summaryList = new ArrayList<QuestionnaireDashboardValueModelBean>();
			if(bean.getComponentType().equals("text") || bean.getComponentType().equals("textarea")) {
				summaryList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryTextList", Map.of("headerId",bean.getHeaderId(),"questionId",bean.getId(),"isShortList","N"));
			}else if(bean.getComponentType().equals("date")) {
				summaryList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireSummaryDateList", Map.of("headerId",bean.getHeaderId(),"questionId",bean.getId(),"isShortList","N"));
			}
			
			ExcelModelBean excelData = new ExcelModelBean();
			for (QuestionnaireDashboardValueModelBean response : summaryList) {
				excelData.setContentValue(response.getName());
				excelData.setContentValue(response.getValue());
				excelData.closeRecord();
			}
			
        	Consumer<Sheet> consumer = sheet -> {
				Cell cell = sheet.getRow(0).getCell(0);
				String cellValue = cell.getStringCellValue();
				String newValue = cellValue.replaceAll("\\{\\{Question\\}\\}",bean.getQuestion());
				cell.setCellValue(newValue);
        	};
			
			return success(ExcelTemplateBuilder.buildExcelDocument(JloExcelTemplate.REPORT_JLO_QTN_Q_SUMMARY,
					Arrays.asList(excelData), consumer));
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}

	}

	@Override
	public ServiceResult<ByteArrayOutputStream> exportQuestionnaireSummaryList(QuestionnaireHeaderModelBean bean) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("E MMM dd yyyy HH:mm:ss O");
			 DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			ExcelModelBean excelData = new ExcelModelBean();
			QuestionnaireHeaderModelBean headerQtn=commonDao.selectOne("questionnaire.findQuestionnaireById", Map.of("id",bean.getId()));
			
			//ดึงคำถาม
			List<QuestionnaireQuestionModelBean> questionList=commonDao.selectList("questionnaire.getQuestionnaireQuestionList", Map.of("headerId",bean.getId()));
			excelData.setContentValue("ชื่อ");
			excelData.setContentValue("เพศ");
			excelData.setContentValue("อายุ");
			
			if(questionList.size()>0) {
				//สร้าง Header Excel => ชื่อ + คำถาม
				for (QuestionnaireQuestionModelBean questionHeader : questionList) {
					excelData.setContentValue(questionHeader.getQuestion());
				}
				excelData.setContentValue("Submitted Date");
				excelData.closeRecord();
			}else {
				excelData.setContentValue("Submitted Date");
				excelData.closeRecord();
			}

			
			//ดึงผู้ตอบคำถาม
			List<QuestionnaireRepondentsModelBean> respondentList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireRespondentList", Map.of("questionnaireHeaderId",bean.getId()));
			
			//สร้าง Data => ชื่อ + คำตอบ
			for (QuestionnaireRepondentsModelBean respondent : respondentList) {
				//เพิ่มชื่อ
				excelData.setContentValue(respondent.getName());
				excelData.setContentValue(respondent.getGender());
				excelData.setContentValue(String.valueOf(respondent.getAge()));
	
				//ดึงคำตอบ
				List<QuestionnaireResponsesModelBean> responseList=commonDao.selectList("questionnaire-dashboard.getQuestionnaireResponseByRespondent",Map.of("respondentId",respondent.getRespondentId()));
				
				//เพิ่มคำตอบ
				for (QuestionnaireQuestionModelBean question : questionList) {
					QuestionnaireResponsesModelBean response=responseList.stream().filter(q->q.getQuestionId()==question.getId()).findFirst().orElse(null);
					if(response==null) {
						excelData.setContentValue("");
					}else {
						if(question.getComponentType().equals("date")) {
							excelData.setContentValue(response.getResponseText());
//							ZonedDateTime zonedDateTime = ZonedDateTime.parse(response.getResponseText(), inputFormatter);
//							excelData.setContentValue(zonedDateTime.format(outputFormatter));
						}else {
							excelData.setContentValue(response.getResponseText());
						}
						
					}
				}
				excelData.setContentValue(respondent.getCreatedDate()==null? "":dateFormat.format(respondent.getCreatedDate()));
				excelData.closeRecord();
			}
			
			Consumer<Sheet> consumer = sheet -> {
				Cell cell = sheet.getRow(0).getCell(0);
				String cellValue = cell.getStringCellValue();
				String newValue = cellValue.replaceAll("\\{\\{FormName\\}\\}",(headerQtn==null? "":headerQtn.getFormName()));
				cell.setCellValue(newValue);
        	};
			
			return success(ExcelTemplateBuilder.buildExcelDocument(JloExcelTemplate.REPORT_JLO_QTN_Q_SUMMARY_LIST,
					Arrays.asList(excelData), consumer));
			
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return fail("500",ex.getMessage());
		}
		
	}
	
	

}
