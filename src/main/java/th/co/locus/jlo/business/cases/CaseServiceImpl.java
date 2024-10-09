package th.co.locus.jlo.business.cases;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.utility.StringUtil;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.common.bean.ExcelModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.common.util.ExcelTemplateBuilder;
import th.co.locus.jlo.util.JloExcelTemplate;
import th.co.locus.jlo.business.casenotilog.CaseNotiLogService;
import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogModelbean;

@Slf4j
@Service
public class CaseServiceImpl extends BaseService implements CaseService {

	@Autowired
	private CaseNotiLogService caseNotiLogService;
	
	@Override
	public ServiceResult<Page<CaseModelBean>> getCaseList(SearchCaseModelBean bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("case.getCaseList", bean, pageRequest));
	}

	@Override
	public ServiceResult<CaseModelBean> getCaseByCaseNumber(String caseNumber) {
		return success(commonDao.selectOne("case.getCaseByCaseNumber", caseNumber));
	}

	@Override
	public ServiceResult<CaseModelBean> createCase(CaseModelBean bean) {

		String caseNumber = commonDao.selectOne("case.generateCaseNumber", bean);
		if (caseNumber != null && caseNumber != "") {
			bean.setCaseNumber(caseNumber);
			// int result = commonDao.update("case.createCase", bean);
			String result = commonDao.selectOne("case.createCaseProcedure", bean);
			log.info("result > " + result);
			if (result != null) {
				
				int resultInsert = commonDao.insert("consulting.createRelConsulting", bean);
				
				//สร้าง Notification ให้ Owner
				CaseNotificationLogModelbean caseNoti=new CaseNotificationLogModelbean();
				caseNoti.setCaseNumber(caseNumber);
				caseNoti.setCreatedBy(bean.getCreatedBy());
				caseNoti.setUserId(bean.getOwner());
				caseNoti.setDescription("คุณได้รับมอบหมาย Case Number : "+caseNumber);
				this.caseNotiLogService.createCaseNotiLog(caseNoti);
				
				return success(commonDao.selectOne("case.getCaseByCaseNumber", caseNumber));
			}
		}
		return fail();
	}

	@Override
	public ServiceResult<CaseModelBean> updateCase(CaseModelBean bean) {

		int result = commonDao.update("case.updateCaseProc", bean);
		if (result > 0) {
			if(!"".equals(bean.getConsultingNumberNewFW()) && bean.getConsultingNumberNewFW() != null) {
				//Follow up
				bean.setConsultingNumber(bean.getConsultingNumberNewFW());
				commonDao.insert("consulting.createRelConsulting", bean);
			}
			
			return success(commonDao.selectOne("case.getCaseByCaseNumber", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ByteArrayOutputStream> exportCaseReport() {
		try {
			ExcelModelBean excelData = new ExcelModelBean();
			for (int i = 0; i <= 10; i++) {
				excelData.setContentValue(i);
				excelData.closeRecord();
			}
			return success(ExcelTemplateBuilder.buildExcelDocument(JloExcelTemplate.REPORT_JLO_CRM01,
					Arrays.asList(excelData), null));
		} catch (Exception e) {
			e.printStackTrace();
			return fail();
		}
	}
}
