package th.co.locus.jlo.business.attachment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.constant.MimeTypeConstants;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@RestController
@RequestMapping("api/attachment")
public class AttachmentMgntController extends BaseController {

	@Value("${attachment.home}")
	private String homeDirectory;

	@Value("${attachment.path.att}")
	private String attPath;

	@Autowired
	private FileService fileService;

	@Autowired
	private AttachmentMgntService attachmentMgntService;

	@GetMapping("/getAttachmentByAttId")
	public void getAttachmentFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "attId", required = true ) Long attId) throws NumberFormatException, Exception {

		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			FileModelBean  fileModelBean = new FileModelBean();
			ServiceResult<FileModelBean> serviceResult = attachmentMgntService.getAttachmentByAttId(attId);
			if (serviceResult.isSuccess()) {
				fileModelBean = serviceResult.getResult();

				String filePath = fileModelBean.getFilePath() +"/"+ fileModelBean.getFileName();
				
				Resource file = fileService.loadFile(filePath);
				file.getFile().getPath();
				System.out.println("Path >>:>>  "+file.getFile().getPath());
				// Open streams.
				File f = new File(file.getFile().getPath());

				if (f.exists() && f.isFile()) {
					
					String fileName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(File.separator) + 1);
					String fileExtension = FilenameUtils.getExtension(fileName);
					response.setContentType(MimeTypeConstants.getMimeType(fileExtension));
					
					response.setHeader("Content-disposition", "inline; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "\"");

					String range = request.getHeader("range");
					response.setHeader("Content-Range", range + (Long.valueOf(f.length()).intValue() - 1));
					response.setHeader("Accept-Ranges", "bytes");
					input = new BufferedInputStream(new FileInputStream(f));
					output = new BufferedOutputStream(response.getOutputStream());

					// Write file contents to response.
					byte[] buffer = new byte[input.available()];
					int length;
					while ((length = input.read(buffer)) > 0) {
						output.write(buffer, 0, length);
					}
				} else {
					
					PrintWriter out = response.getWriter();
					out.write("File Unavailable.");
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.flush();
					output.close();
				}
				if (input != null)
					input.close();
			} catch (Exception e) {
			}
		}
	}

	public String getSysPath(String path) {
		return homeDirectory + path;
	}

}
