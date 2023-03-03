package in.bushansirgur.springbootfileupload.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uploadFile(MultipartFile file);
	void downloadFile(String bucketName,String file);
}
