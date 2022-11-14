package com.clinistats.helpdesk.repositry;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.domain.Status;
import com.clinistats.helpdesk.model.ProviderFacilityMappingModel;

@Repository
public interface ProviderFacilityPermissionMappingRepositry extends JpaRepository<ProviderFacilityMappingModel, Long> {

	@Query("SELECT model FROM ProviderFacilityMappingModel model WHERE model.providerId= :providerId AND model.status= :status  AND model.resource= :resource")
	List<ProviderFacilityMappingModel> getAllFacilityMappedByProvider(Long providerId, Status status, boolean resource);

	ProviderFacilityMappingModel findByFacilityIdAndProviderIdAndResource(Long facilityId, Long providerId,
			boolean resource);

	List<ProviderFacilityMappingModel> findByProviderId(Long providerId);

	@Query("SELECT model FROM ProviderFacilityMappingModel model WHERE model.facilityId= :facilityId AND model.status= :status AND model.resource= :resource")
	List<ProviderFacilityMappingModel> getAllProviderByFacility(Long facilityId, Status status, boolean resource);

	@Query("SELECT model FROM ProviderFacilityMappingModel model WHERE model.facilityId= :facilityId AND model.status= :status")
	List<ProviderFacilityMappingModel> getAllProviderByFacility(Long facilityId, Status status);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProviderFacilityMappingModel SET status= :status WHERE providerId= :providerId AND facilityId not in( :facilityId) AND resource=:resource")
	void updateFacStatusExcludingFacilityId(Long providerId, Status status, List<Long> facilityId, boolean resource);
}
