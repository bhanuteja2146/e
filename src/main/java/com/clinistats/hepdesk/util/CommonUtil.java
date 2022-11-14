package com.clinistats.hepdesk.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author lukman.d
 *
 */
@Component
public class CommonUtil {

	@Autowired
	private AES encryptDecryptUtil;

	/**
	 * Process converts list of long to comma separated string
	 * 
	 * @param ids
	 * @return
	 */
	public String convertListToString(List<Long> ids) {
		if (!CollectionUtils.isEmpty(ids)) {
			return ids.stream().map(String::valueOf).collect(Collectors.joining(","));
		}
		return "";
	}

	/**
	 * Process converts comma separated string to list of long
	 * 
	 * @param ids
	 * @return
	 */
	public List<Long> convertStringToList(String id) {
		if (id != null && !id.isEmpty()) {

			return Arrays.asList(id.split(",")).stream().filter(s -> s != null && !s.isEmpty()).map(Long::parseLong)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	/**
	 * Process converts comma separated string to list of long
	 * 
	 * @param ids
	 * @return
	 */
	public List<String> convertStringToListString(String id) {
		if (id != null && !id.isEmpty()) {

			return Arrays.asList(id.split(",")).stream().filter(s -> s != null && !s.isEmpty())
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	/*
	 * Process retrieves assigned/mapped facilities to providers
	 * 
	 * @param webRequest
	 * 
	 * @return
	 */
	public List<Long> getAssigedFacilites(HttpServletRequest webRequest) {

		String encodedFacilities = webRequest.getHeader("x-facilities");

		if (!StringUtils.isBlank(encodedFacilities)) {
			return convertStringToList(encryptDecryptUtil.decrypt(encodedFacilities));
		}

		return Collections.emptyList();
	}

	/*
	 * Process retrieves assigned/mapped facilities to providers
	 * 
	 * @param webRequest
	 * 
	 * @return
	 */
	public List<String> getAssigedFacilitesAsListString(HttpServletRequest webRequest) {

		String encodedFacilities = webRequest.getHeader("x-facilities");

		if (!StringUtils.isBlank(encodedFacilities)) {
			return convertStringToListString(encryptDecryptUtil.decrypt(encodedFacilities));
		}

		return Collections.emptyList();
	}
}
