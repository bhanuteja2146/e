package com.clinistats.helpdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.helpdesk.domain.Facility;
import com.clinistats.helpdesk.model.FacilityModel;
import com.clinistats.helpdesk.request.UpdateFacilityRequest;
import com.clinistats.helpdesk.response.GetIdNameDto;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

	
	Facility toDomain(UpdateFacilityRequest request);

	FacilityModel toModel(Facility facility);

	Facility toDomain(FacilityModel model);

	List<GetIdNameDto> toCustomDomain(List<FacilityModel> provider);

	List<Facility> toDomains(List<FacilityModel> list);

//	FacilityAuditLogDomain toFacilityDomain(FacilityAuditLogRequest facilityRequest);
}
