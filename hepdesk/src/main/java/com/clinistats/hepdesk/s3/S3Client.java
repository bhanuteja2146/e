/**
 * 
 */
package com.clinistats.hepdesk.s3;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

 
@Component
public class S3Client {

	private static AmazonS3 client=AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
	
	@Value("${aws.publicBucket}")
    private String publicBucket;
	 
	public AmazonS3 gets3Client() {
		return client;
	}

	public String getPublicBucket() {
		return publicBucket;
	}
 

	public String generatePreSignUrl(String bucketName,String key) {
		java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 120000;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
	}
	
	
	@Async
	public void deleteFile(String bucketName,final String keyName) {
        //LOGGER.info("Deleting file with name= " + keyName);
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        client.deleteObject(deleteObjectRequest);
        //LOGGER.info("File deleted successfully.");
    }

	

}



