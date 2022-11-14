/**
 * 
 */
package com.clinistats.hepdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.hepdesk.domain.ProviderFacilityPermssionMappingDomain;
import com.clinistats.hepdesk.model.ProviderFacilityMappingModel;

/**
 * @author lukman.d
 *
 */
@Mapper(componentModel = "spring")
public interface ProviderFacilityPermissionMappingMapper {

	ProviderFacilityPermssionMappingDomain toDomain(ProviderFacilityMappingModel userProfileModel);

	ProviderFacilityMappingModel toModel(ProviderFacilityPermssionMappingDomain userProfile);

	List<ProviderFacilityMappingModel> toModel(List<ProviderFacilityPermssionMappingDomain> userProfiles);

	List<ProviderFacilityPermssionMappingDomain> toDomains(
			List<ProviderFacilityMappingModel> allFacilityMappedByProvider);
}
