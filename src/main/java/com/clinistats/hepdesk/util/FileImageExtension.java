package com.clinistats.hepdesk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileImageExtension {
	
	static Random random = new Random();


	private static final Logger logger = LoggerFactory.getLogger(FileImageExtension.class);

	public static ResponseEntity<InputStreamResource> imageExtension(String completeFileName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(completeFileName));
		} catch (FileNotFoundException e) {
			logger.debug("  getRecordDocument / download file exception", e);
		}
		String extension = FilenameUtils.getExtension(completeFileName);

		if (extension.contains("jpg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);

		} else if (extension.contains("pdf")) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
		} else if (extension.contains("jpeg")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
		} else if (extension.contains("png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
		}

		return null;
	}

	public static String storeProfilePicture(String id,MultipartFile file,String basePath,String extra) {
		String uploadsDir = null;
		String baseDir = basePath;
		if(extra != null) {
		String facilityFolder=extra.concat(id);
		uploadsDir = baseDir.concat(File.separator).concat(facilityFolder);
		}
		else {
		 uploadsDir = baseDir.concat(File.separator).concat(id);
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String basename = FilenameUtils.getBaseName(fileName);
		String randomNumber = String.valueOf(random.ints(10000, 99999).findAny().getAsInt());
		basename = basename+randomNumber;
		String extension = FilenameUtils.getExtension(fileName);


		if (!checkIfDirectoryExists(uploadsDir)) {
			logger.debug(" storeFileOnSystem, uploads_dir does not exists");
			
			File fdir = new File(uploadsDir);
			fdir.mkdir();
		}
		String newFileName = basename.concat(".").concat(extension);

		logger.debug(" newFileName = " + newFileName);

		logger.debug(" uploads_dir, photoPath = " + uploadsDir);
		try {
			if (fileName.contains("..")) {
				logger.debug(" Invalid filename  path sequence !!!");
			}
			Path fileStorageLocation = Paths.get(uploadsDir).toAbsolutePath().normalize();
			logger.debug(" filestoraelocation = " + fileStorageLocation);
			Path targetLocation = fileStorageLocation.resolve(newFileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			logger.debug(" Catch, Exception in storeProfilePicture method ");
		}

		return newFileName;

	}

	public static boolean checkIfDirectoryExists(String folderPath) {
		File dir = new File(folderPath);
		return dir.exists();
	}
}
