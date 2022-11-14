package com.clinistats.helpdesk.util;

import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	
	public static boolean isValidFileTypes(MultipartFile[] files, String[] validTypes) {
		logger.info("Entered method: isValidFileTypes,input validTypes="+validTypes.toString());
		for(int i=0; i < files.length; i++) {
			String fileName = StringUtils.cleanPath(files[i].getOriginalFilename());
			String extension = FilenameUtils.getExtension(fileName);
			logger.info("Method: isValidFileTypes, fileName="+fileName+" , extension="+extension);
			boolean found = Arrays.stream(validTypes).anyMatch(extension::contains);
			if(!found) { 
				logger.info(" Method: isValidFileTypes, invalid file extension = "+fileName);
				return false;
			}
		}
		return true;   	 	 
	}
	
}
