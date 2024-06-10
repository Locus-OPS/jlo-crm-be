package th.co.locus.jlo.system.email;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.util.file.FileService;

@RestController
@RequestMapping("public_email")
public class EmailReadController {
	
	@Value("${attachment.path.att}")
	private String attachmentPath;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private FileService fileService;

	@GetMapping(value = "/image/locus.png")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(HttpServletRequest request) {
		try {
			emailService.insertReadLog(request.getParameter("ref"), request.getParameter("email"));
			String fileName = "locus.png";
			Resource file = fileService.loadFile(attachmentPath + File.separator + "blank.png");
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(file);
		} catch (Exception e) {
			return ResponseEntity.ok().body(null);
		}
	}

}
