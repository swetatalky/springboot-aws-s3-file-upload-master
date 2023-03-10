package in.bushansirgur.springbootfileupload.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.bushansirgur.springbootfileupload.service.AWSS3Service;



@RestController
@RequestMapping("/api/file")
public class UploadFileController {
	
	@Autowired
	private AWSS3Service awsS3Service;
	
	@PostMapping
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
		File f=new File("E:\\xlxsfolder\\six_file.xlsx");
		String publicURL = awsS3Service.uploadFile(f);
		Map<String, String> response = new HashMap<>();
		response.put("publicURL", publicURL);
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/{bucketName}/{file}")
	public void downloadFile(@PathVariable("bucketName") String  bucketName,@PathVariable("file") String  file) {
		 awsS3Service.downloadFile(bucketName,file);
		
		//return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}
}
