package th.co.locus.jlo.system.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Slf4j
@Service
public class FileServiceImpl extends BaseService implements FileService {

	@Value("${attachment.home}")
	private String homeDirectory;

	@Override
	public void saveFile(MultipartFile srcFile, String folder, String fileName) throws IOException {
		File destFile = Paths.get(homeDirectory).resolve(folder).resolve(fileName).toFile();
		FileUtils.writeByteArrayToFile(destFile, srcFile.getBytes());
	}

	@Override
	public Resource loadFile(String filePath) {
		try {
			Path file = Paths.get(homeDirectory).resolve(filePath);

			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public ServiceResult<FileModelBean> createAttachment(FileModelBean bean) {
		int rows = commonDao.insert("file.createAttachment", bean);
		if (rows > 0) {
			return success(bean);
		}
		return fail();
	}

}
