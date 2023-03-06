package in.bushansirgur.springbootfileupload.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class AWSS3Service implements FileService{
	@Autowired
	private AmazonS3Client awsS3Client;
	
	@Override
	public String uploadFile(MultipartFile file) {
	
		String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		System.out.println("StringUtils.getFilename(file.getOriginalFilename())"+StringUtils.getFilename(file.getOriginalFilename()));
		String key = StringUtils.getFilename(file.getOriginalFilename()) ;
		
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(file.getSize());
		long length=file.getSize();
		System.out.println("length is :"+length);
		System.out.println("file.getContentType():"+file.getContentType());
		System.out.println("metaData length is :"+metaData.getContentLength());
		metaData.setContentType(file.getContentType());
		
		try {
			awsS3Client.putObject("dependencycheck12", key, file.getInputStream(), metaData);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
		}
		
		//awsS3Client.setObjectAcl("dependencycheck12", key, CannedAccessControlList.PublicRead);
		System.out.println("key is :"+key);
		return awsS3Client.getResourceUrl("dependencycheck12", key);
	}
	public  long getFileSizeKiloBytes(File file) {
		double x= (double) file.length() / 1024 ;
		return (long)x;
	}
	
	private  long getFileSizeBytes(File file) {
		return file.length() ;
	}
	@Override
	public String uploadFile(File file) {
	
		//String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		//System.out.println("StringUtils.getFilename(file.getOriginalFilename())"+StringUtils.getFilename(file.getOriginalFilename()));
		System.out.println(file.getName());
		String key = file.getName() ;
		
		
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(getFileSizeBytes(file));
		long length=getFileSizeKiloBytes(file);
		System.out.println("length is :"+length);
		System.out.println("metaData length is :"+metaData.getContentLength());
		metaData.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		try {  
		InputStream targetStream = new FileInputStream(file);
		
			awsS3Client.putObject("dependencycheck12", key, targetStream, metaData);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
		}
		//return "abc";
		
	//awsS3Client.setObjectAcl("dependencycheck12", key, CannedAccessControlList.PublicRead);
		System.out.println("key is :"+key);
		return awsS3Client.getResourceUrl("dependencycheck12", key);
	}
	@Override
	public void downloadFile(String bucketName,String fileName) {
		try {
//		S3Object obj=	awsS3Client.getObject(bucketName, fileName);
//		
//		 File localFile = new File("localFile.xlsx");
//		FileUtils.copyToFile(obj.getObjectContent(), localFile);
		
		S3Object obj = awsS3Client.getObject(new GetObjectRequest(bucketName, fileName));

		InputStream inputStream = obj.getObjectContent();

		long fileLength = obj.getObjectMetadata().getContentLength();
		System.out.println("fileLeength is :"+fileLength);
		File localFile = new File("localFile6.xlsx");
		try {
			//FileUtils.copyToFile(obj.getObjectContent(), localFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		catch(AmazonS3Exception ex) {
			System.out.println("File does not exists");
			callMe();
		}
		finally {
			
		}
		System.out.println("File does not exists after  call me");
	}
	
	public void callMe() {
		System.out.println("File does not exists inside call me");
	}
}
