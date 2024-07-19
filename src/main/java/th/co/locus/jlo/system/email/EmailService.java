package th.co.locus.jlo.system.email;

import java.io.File;
import java.util.List;
import java.util.Map;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

public interface EmailService {

	public ServiceResult<Boolean> insertReadLog(String refName, String email);
	
	public void sendEmail(String from, String to, String subject, String message, String fromName) throws Exception;

	public void sendEmailTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String message, String fromName, String templateName,
			Map<String, Object> params) throws Exception;

	public void sendEmailAttTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String message, String fromName, Map<String, Object> params, File attFile, String fileName) throws Exception;
	
	public void sendEmailMultiAttTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String message, String fromName, Map<String, Object> params, List<FileModelBean> attFileList) throws Exception;

	
}
