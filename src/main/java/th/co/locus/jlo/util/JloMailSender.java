package th.co.locus.jlo.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Slf4j
@Component
public class JloMailSender {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration freemarkerConfig;

	@Value("${jlo.mail.from}")
	private String mailFrom;

	public void sendMail(String from, String to, String subject, String body) throws Exception {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);

		log.info("Sending email...");

		mailSender.send(mail);

		log.info("Done!");
	}

	public void sendMail(String from, String to, String cc, String bcc, String subject, String body) throws Exception {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
		if (cc != null && !cc.isEmpty()) {
			mail.setCc(cc);
		}
		if (bcc != null && !bcc.isEmpty()) {
			mail.setBcc(bcc);
		}
		mail.setSubject(subject);
		mail.setText(body);

		log.info("Sending email...");

		mailSender.send(mail);

		log.info("Done!");
	}

	public void sendEmailWithTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String body) throws Exception {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("body", body);

		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
		Template template = freemarkerConfig.getTemplate("template.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		helper.setFrom(mailFrom, from);
		helper.setTo(to);
		if (cc != null && cc[0] != "" && cc[0] != null) {
			helper.setCc(cc);
		}
		if (bcc != null && bcc[0] != "" && bcc[0] != null) {
			helper.setBcc(bcc);
		}
		helper.setSubject(subject);
		helper.setText(text, true);

		mailSender.send(message);
	}

	public void sendEmailAttWithTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String body, File attFile)
			throws Exception {

		System.setProperty("mail.mime.encodeparameters", "false");
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("body", body);

		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");

		Template template = freemarkerConfig.getTemplate("template.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		helper.setFrom(mailFrom, from);
		helper.setTo(to);
		if (cc != null && cc[0] != "" && cc[0] != null) {
			helper.setCc(cc);
		}
		if (bcc != null && bcc[0] != "" && bcc[0] != null) {
			helper.setBcc(bcc);
		}
		helper.setSubject(subject);
		helper.setText(text, true);
		if (attFile != null) {
			helper.addAttachment(MimeUtility.encodeText(attFile.getName(), "UTF-8", null), attFile);
		}
		log.info("Sending email...");
		mailSender.send(message);
		log.info("Done!");
	}

	public void sendEmailAttMultipleWithTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String body,
			List<MultipartFile> attFileList) throws Exception {

		System.setProperty("mail.mime.encodeparameters", "false");
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("body", body);

		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");

		Template template = freemarkerConfig.getTemplate("template.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		helper.setFrom(mailFrom, from);
		helper.setTo(to);
		if (cc != null && cc[0] != "" && cc[0] != null) {
			helper.setCc(cc);
		}
		if (bcc != null && bcc[0] != "" && bcc[0] != null) {
			helper.setBcc(bcc);
		}
		helper.setSubject(subject);
		helper.setText(text, true);
		if (attFileList != null) {
			for (int i = 0; i < attFileList.size(); i++) {
				helper.addAttachment(MimeUtility.encodeText(attFileList.get(i).getOriginalFilename(), "UTF-8", null), attFileList.get(i));
			}
		}
		log.info("Sending email...");
		mailSender.send(message);
		log.info("Done!");
	}

	public void sendEmailAttWithTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String body, File attFile,
			String fileName) throws Exception {

		System.setProperty("mail.mime.encodeparameters", "false");
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("body", body);

		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");

		Template template = freemarkerConfig.getTemplate("template.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		helper.setFrom(mailFrom, from);
		helper.setTo(to);
		if (cc[0] != "" && cc[0] != null) {
			helper.setCc(cc);
		}
		if (bcc[0] != "" && bcc[0] != null) {
			helper.setBcc(bcc);
		}
		helper.setSubject(subject);
		helper.setText(text, true);
		helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", null), attFile);
		log.info("Sending email...");
		mailSender.send(message);
		log.info("Done!");
	}
	
	
	public void sendEmailAttMultiWithTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String body,  List<FileModelBean> attFileList) throws Exception {
//		// File attFile,String fileName
//		System.setProperty("mail.mime.encodeparameters",  "false");
//		MimeMessage message = mailSender.createMimeMessage();
//
//		// MimeMessageHelper helper = new MimeMessageHelper(message);
//		MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
//
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("body", body);
//
//		// set loading location to src/main/resources
//		// You may want to use a subfolder such as /templates here
//		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
//
//		Template template = freemarkerConfig.getTemplate("welcome.ftl");
//		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//
//		helper.setFrom("",from);
//		helper.setTo(to);
//		if (cc[0] != "" && cc[0] != null) {
//			helper.setCc(cc);
//		}
//		if (bcc[0] != "" && bcc[0] != null) {
//			helper.setBcc(bcc);
//		}
//		
//		helper.setSubject(subject);
//		helper.setText(text, true);
//		
//		//helper.addAttachment(MimeUtility.encodeText(fileName,"UTF-8",null), attFile);
//		
//		
//		for(FileModelBean attFile: attFileList) {
//			String filePath = attFile.getFilePath();
//			Resource fileObject = fileService.loadFile(filePath);
//			String fullPath = fileObject.getFile().getPath();
//			
//			log.info(attFile.getFileName()+" Path >>:>>  " + fullPath);
//			// Open streams.
//			File myFilePath = new File(fullPath);
//			
//			helper.addAttachment(MimeUtility.encodeText(attFile.getFileName(),"UTF-8",null), myFilePath);
//			
//		}
//		
//		log.info("Sending email...");
//		mailSender.send(message);
//		log.info("Done!");
	}

}
