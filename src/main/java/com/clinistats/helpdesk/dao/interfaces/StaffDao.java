package com.clinistats.helpdesk.dao.interfaces;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.StaffFilter;
import com.clinistats.helpdesk.domain.Customer;
import com.clinistats.helpdesk.response.GetStaffResponse;

public interface StaffDao {

	String addStaff(Customer provider);

	Customer getById(String id);

	String updateStaff(Customer provider);

	GetStaffResponse getAll(StaffFilter filter, Pagination pagination);

//	GetProviderResponse getALLProvidersTTD(TTDProviderFilter providerFilter, Pagination pagination);

//	List<String> getAllSpecialities(SpecialityFilter specialityFilter);

	boolean deleteById(String ids);

//	void deleteProviderById(String id);

//	List<Object[]> getAllProvidersIds();
//
//	List<GetIdNameDto> getProviderNamesByIds(String ids);
//
//	List<GetIdName> getALLProvidersByName(ProviderFilter filter);
//
//	List<GetIdName> getProviderIDNamesListBySearch(ProviderFilter filter);
//
//	ResponseEntity<InputStreamResource> addProviderPhoto(MultipartFile file, String providerId);
//
//	ResponseEntity<InputStreamResource> getProviderProfilePictureById(String providerId);
//
//	GetIdNameDto getProviderNameById(String ids);
//
	Long countByCellPhone(String cellPhone);
//
//	List<ProviderIdSpeciality> getAllProvidersBySpecialities(FacilitySpecialityFilter specialityFilter);
//
//	void generateStaffProvider(StaffproviderAuditLogDomain domain);
//
//	StaffProviderAuditLogResponse getAllStaffProvidersLogsWithPagination(StaffProvidersAuditLogFilter filter,
//			Pagination pagination);
//
//	String getProviderDefaultScreen(String id);
//
//	List<Staff> getProviders();
//
//	Staff updateProviderWithAnalyticStatus(Staff provider);
//
//	String addProviderSignPhoto(MultipartFile file, String providerId);
//
//	ResponseEntity<InputStreamResource> getProviderSignPictureById(String id);
//
//	List<GetIdName> getProviderNamesAndByIds(String providerId);
//
//	List<GetIdName> getProviderIDNamesListBySearch(@Valid ProviderFilter filter, List<Long> assigedFacilites);

	Customer delete(String ids);
}
