package com.clinistats.helpdesk.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class S3FileUpload {

	public static String uploadFile(MultipartFile rxImage, S3Client s3Client) throws IOException {
		File rxImageFile = convertMultiPartToFile(rxImage);
		String fileName = generateFileName(rxImage);
		s3Client.gets3Client().putObject(s3Client.getPublicBucket(), fileName, rxImageFile);
		rxImageFile.delete();
		return s3Client.gets3Client().getUrl(s3Client.getPublicBucket(), fileName).toString();
	}

	public static String uploadImage(MultipartFile rxImage, S3Client s3Client) throws IOException {
		File rxImageFile = convertMultiPartToFile(rxImage);
		String fileName = generateFileName(rxImage);
		fileName = "images/" + fileName;
		s3Client.gets3Client().putObject(s3Client.getPublicBucket(), fileName, rxImageFile);
		rxImageFile.delete();
		return s3Client.gets3Client().getUrl(s3Client.getPublicBucket(), fileName).toString();
	}

	public static File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	@Async
	public void deleteFileFromS3Bucket(String fileName, S3Client s3Client) {
		try {
			s3Client.gets3Client().deleteObject(new DeleteObjectRequest(s3Client.getPublicBucket(), fileName));
		} catch (AmazonServiceException ex) {
			// logger.error("error [" + ex.getMessage() + "] occurred while removing [" +
			// fileName + "] ");
			System.out.println("error [\" + ex.getMessage() + \"] occurred while removing [\" + fileName + \"] ");
		}
	}

	public static String generateThumbnail(String url) {
		url = url.replace("images", "thumbnail/images");
		return url;
	}

}
