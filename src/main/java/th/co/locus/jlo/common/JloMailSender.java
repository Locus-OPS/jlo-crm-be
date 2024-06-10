package th.co.locus.jlo.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JloMailSender {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private Configuration freemarkerConfig;
	
	
	public void sendMail(String from, String to, String subject, String body)  throws Exception {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);

		log.info("Sending email...");

		mailSender.send(mail);
		 
		log.info("Done!");
	}

	public void sendMail(String from, String to, String cc, String bcc, String subject, String body)  throws Exception {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
        if (cc != null && !cc.isEmpty()){
    		mail.setCc(cc);
        }
        if (bcc != null && !bcc.isEmpty()){
    		mail.setBcc(bcc);
        }
		mail.setSubject(subject);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
        
		// Template template = freemarkerConfig.getTemplate("welcome.ftl");
		// String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<String, Object>());
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
        
        // set loading location to src/main/resources
        // You may want to use a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
        
        Template template = freemarkerConfig.getTemplate("welcome.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        
        helper.setFrom(from);
        helper.setTo(to);
        if (cc[0] != ""){
            helper.setCc(cc);
        }
        if (bcc[0] != ""){
            helper.setBcc(bcc);
        }
        helper.setSubject(subject);
        helper.setText(text, true);

        
        mailSender.send(message);
    }
	
	
	public void sendEmailAttWithTemplate(String from, String[] to, String[] cc, String[] bcc, String subject, String body, File attFile) throws Exception {
        
		MimeMessage message = mailSender.createMimeMessage();

        //MimeMessageHelper helper = new MimeMessageHelper(message);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("body", body);
        
        // set loading location to src/main/resources
        // You may want to use a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/emailTemplates");
        
        Template template = freemarkerConfig.getTemplate("welcome.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        
        helper.setFrom(from);
        helper.setTo(to);
        if (cc[0] != ""){
            helper.setCc(cc);
        }
        if (bcc[0] != ""){
            helper.setBcc(bcc);
        }
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.addAttachment(attFile.getName(), attFile);

        mailSender.send(message);
    }

	

}
