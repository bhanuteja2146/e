package com.clinistats.hepdesk.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clinistats.hepdesk.dao.interfaces.ProvFacPermissionMappingDao;
import com.clinistats.hepdesk.domain.ProviderFacilityPermssionMappingDomain;
import com.clinistats.hepdesk.domain.Status;
import com.clinistats.hepdesk.mapper.ProviderFacilityPermissionMappingMapper;
import com.clinistats.hepdesk.model.ProviderFacilityMappingModel;
import com.clinistats.hepdesk.repositry.ProviderFacilityPermissionMappingRepositry;

@Repository
public class ProvFacPermissionMappingDaoImpl implements ProvFacPermissionMappingDao {

	@Autowired
	private ProviderFacilityPermissionMappingRepositry provFacRepo;

	@Autowired
	private ProviderFacilityPermissionMappingMapper permissionMappingMapper;

	@Override
	public void updateFacStatusExcludingFacilityId(long providerId, Status status, List<Long> facilityId,
			boolean resource) {
		provFacRepo.updateFacStatusExcludingFacilityId(providerId, status, facilityId, resource);
	}

	@Override
	public ProviderFacilityPermssionMappingDomain findByFacilityIdAndProviderId(Long facilityId, long providerId,
			boolean resource) {
		return permissionMappingMapper
				.toDomain(provFacRepo.findByFacilityIdAndProviderIdAndResource(facilityId, providerId, resource));
	}

	@Override
	public void add(ProviderFacilityPermssionMappingDomain exisitngPermission) {
		provFacRepo.save(permissionMappingMapper.toModel(exisitngPermission));
	}

	@Override
	public List<ProviderFacilityPermssionMappingDomain> getAllFacilityMappedByProvider(Long id, Status status,
			boolean resource) {

		List<ProviderFacilityMappingModel> allFacilityMappedByProvider = provFacRepo.getAllFacilityMappedByProvider(id,
				status, resource);

		return permissionMappingMapper.toDomains(allFacilityMappedByProvider);
	}

	@Override
	public List<ProviderFacilityPermssionMappingDomain> getAllProviderMappedByFacility(Long id, Status status,
			boolean resource) {
		if (id != null && id > 0) {
			List<ProviderFacilityMappingModel> allProviderByFacility = provFacRepo.getAllProviderByFacility(id, status,
					resource);

			return permissionMappingMapper.toDomains(allProviderByFacility);
		}

		return Collections.emptyList();
	}

	@Override
	public List<ProviderFacilityPermssionMappingDomain> getAllProviderMappedByFacility(Long id, Status status) {
		if (id != null && id > 0) {
			List<ProviderFacilityMappingModel> allProviderByFacility = provFacRepo.getAllProviderByFacility(id, status);

			return permissionMappingMapper.toDomains(allProviderByFacility);
		}

		return Collections.emptyList();
	}

}
