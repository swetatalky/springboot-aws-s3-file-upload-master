package in.bushansirgur.springbootfileupload.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uploadFile(MultipartFile file);
	String uploadFile(File file);
	void downloadFile(String bucketName,String file);
}
