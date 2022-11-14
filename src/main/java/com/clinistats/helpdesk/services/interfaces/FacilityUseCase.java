package com.clinistats.helpdesk.services.interfaces;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.clinistats.helpdesk.config.filter.FacilityFilter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.domain.Facility;
import com.clinistats.helpdesk.response.GetFacilityResponse;

public interface FacilityUseCase {
	String addFacility(Facility facility);

//	void generateFac	ility(FacilityAuditLogDomain domain);

	String uploadFacilityImage(MultipartFile file, String id);

	String updateFacility(Facility facility);

	Facility getFacilityById(long id);

	Long countByName(String name);

	Long countByPhone(String phone);

	void deleteFacilities(List<String> ids);

	Facility deleteFacilityById(String id);

	GetFacilityResponse getFacility(FacilityFilter filter, Pagination pagination);

//	List<GetIdNameColor> getFacilityIDNamesListBySearch(@Valid FacilityFilter request);
//
//	FacilityAuditLogResponse getAllFacilityLogsWithPagination(FacilityAuditLogFilter filter, Pagination pagination);

	ResponseEntity<InputStreamResource> getFacilityImageById(String id);

}
