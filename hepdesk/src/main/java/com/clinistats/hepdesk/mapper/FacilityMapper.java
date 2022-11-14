package com.clinistats.hepdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.hepdesk.domain.Facility;
import com.clinistats.hepdesk.model.FacilityModel;
import com.clinistats.hepdesk.request.UpdateFacilityRequest;
import com.clinistats.hepdesk.response.GetIdNameDto;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

	
	Facility toDomain(UpdateFacilityRequest request);

	FacilityModel toModel(Facility facility);

	Facility toDomain(FacilityModel model);

	List<GetIdNameDto> toCustomDomain(List<FacilityModel> provider);

	List<Facility> toDomains(List<FacilityModel> list);

//	FacilityAuditLogDomain toFacilityDomain(FacilityAuditLogRequest facilityRequest);
}
