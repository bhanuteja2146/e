package com.clinistats.hepdesk.dao.interfaces;

import java.util.List;

import com.clinistats.hepdesk.domain.ProviderFacilityPermssionMappingDomain;
import com.clinistats.hepdesk.domain.Status;

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
