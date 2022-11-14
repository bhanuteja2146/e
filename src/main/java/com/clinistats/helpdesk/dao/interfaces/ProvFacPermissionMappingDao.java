package com.clinistats.helpdesk.dao.interfaces;

import java.util.List;

import com.clinistats.helpdesk.domain.ProviderFacilityPermssionMappingDomain;
import com.clinistats.helpdesk.domain.Status;

public interface ProvFacPermissionMappingDao {

	void updateFacStatusExcludingFacilityId(long parseLong, Status inactive, List<Long> convertedId, boolean resource);

	ProviderFacilityPermssionMappingDomain findByFacilityIdAndProviderId(Long fac, long parseLong, boolean resource);

	void add(ProviderFacilityPermssionMappingDomain exisitngPermission);

	List<ProviderFacilityPermssionMappingDomain> getAllFacilityMappedByProvider(Long id, Status status,
			boolean resource);

	List<ProviderFacilityPermssionMappingDomain> getAllProviderMappedByFacility(Long id, Status status,
			boolean resource);

	List<ProviderFacilityPermssionMappingDomain> getAllProviderMappedByFacility(Long id, Status status);

}
