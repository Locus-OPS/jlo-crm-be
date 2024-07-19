package th.co.locus.jlo.system.email;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;
import th.co.locus.jlo.util.JloMailSender;

@Service
public class EmailServiceImpl extends BaseService implements EmailService {
	
	@Autowired
	private JloMailSender jloMailSender;
	
	@Override
	public ServiceResult<Boolean> insertReadLog(String refName, String email) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			Map<String, String> param = new HashMap<String, String>();
			param.put("refName", refName);
			param.put("email", email);
			param.put("ipAddress", request.getRemoteAddr());
			param.put("xForwardedFor", request.getHeader("X-Forwarded-For"));
			commonDao.insert("email.insertReadLog", param);
		} catch (Exception e) {
			
		}
		return success(Boolean.TRUE);
	}

	@Override
	public void sendEmail(String from, String to, String subject, String message, String fromName) throws Exception {
		jloMailSender.sendMail(from, to, subject, message);

	}

	@Override
	public void sendEmailTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String message,
			String fromName, String templateName, Map<String, Object> params) throws Exception {

		jloMailSender.sendEmailWithTemplate(from, to, cc, bcc, subject, message);

	}

	@Override
	public void sendEmailAttTemplate(String from, String[] to, String[] cc, String[] bcc, String subject,
			String message, String fromName, Map<String, Object> params, File attFile, String fileName)
			throws Exception {

		jloMailSender.sendEmailAttWithTemplate(from, to, cc, bcc, subject, message, attFile, fileName);

	}

	@Override
	public void sendEmailMultiAttTemplate(String from, String[] to, String[] cc, String[] bcc, String subject,
			String message, String fromName, Map<String, Object> params, List<FileModelBean> attFileList)
			throws Exception {

		jloMailSender.sendEmailAttMultiWithTemplate(from, to, cc, bcc, subject, message, attFileList);

	}

}
