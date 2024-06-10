package th.co.locus.jlo.util.file;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.file.modelbean.FileModelBean;

public interface FileService {

	public void saveFile(MultipartFile srcFile, String folder, String fileName) throws IOException;
	public Resource loadFile(String filePath);
	public ServiceResult<FileModelBean> createAttachment(FileModelBean bean);

}
