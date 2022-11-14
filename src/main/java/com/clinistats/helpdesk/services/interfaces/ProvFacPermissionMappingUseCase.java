package com.clinistats.helpdesk.services.interfaces;

import java.util.List;
import java.util.Map;

import com.clinistats.helpdesk.domain.ProviderFacilityPermssionMappingDomain;
import com.clinistats.helpdesk.domain.Status;

public interface ProvFacPermissionMappingUseCase {

	List<Long> mapFacilityToProviders(String providerId, List<String> facilitId, boolean b);

//	Map<Long, List<Long>> deassignFacilityToProviders(String providerId, List<String> facilityId);

	Map<Long, List<Long>> getMappedFacilityListByStatus(String providerId, Status status, boolean resource);

	Map<Long, List<ProviderFacilityPermssionMappingDomain>> getMappedProviderListByFacilityAndStatus(String facilityId,
			Status status, String resource);

	Map<Long, List<String>> getMappedUserNameByFacility(String facilityId, Status status, String resource);

}
