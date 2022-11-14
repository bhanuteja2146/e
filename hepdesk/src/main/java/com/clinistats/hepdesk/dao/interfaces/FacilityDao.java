package com.clinistats.hepdesk.dao.interfaces;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.clinistats.hepdesk.config.filter.FacilityFilter;
import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.domain.Facility;
import com.clinistats.hepdesk.response.GetFacilityResponse;
import com.clinistats.hepdesk.response.GetIdName;
import com.clinistats.hepdesk.response.GetIdNameDto;

public interface FacilityDao {

	String addFacility(Facility facility);

	Facility getFacilityById(long id);

	GetFacilityResponse getAllFacilities(Pageable paging);

	String updateFacility(Facility facility);

	String deleteFacilities(List<String> ids);

	Facility deleteFacilityById(String id);

	List<Object[]> getAllFacilitiesIds();

	GetFacilityResponse getFacility(FacilityFilter filter, Pagination pagination);

	List<GetIdNameDto> getFacilityByIdAndNames(String ids);

//	List<GetIdNameColor> getFacilityIDNamesListBySearch(@Valid FacilityFilter request);
	boolean existByName(String name);

	GetIdNameDto getFacilityByIdAndName(String id);

	Long countByName(String name);

	Long countByPhone(String phone);

//	void generateFacility(FacilityAuditLogDomain domain);
//	FacilityAuditLogResponse getAllFacilityLogsWithPagination(FacilityAuditLogFilter filter, Pagination pagination);
	String uploadFacilityImage(MultipartFile file, String id);

	ResponseEntity<InputStreamResource> getFacilityImageById(String id);

//	List<Facility> getFaclities();

	Facility updateFacilityWithAnalyticStatus(Facility obj);

	List<GetIdName> getFacilityIdAndNamesById(String facilityId);
//	List<GetIdNameColor> getFacilityIDNamesListBySearch(FacilityFilter request, List<Long> assigedFacilites);

}
