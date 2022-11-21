package com.clinistats.hepdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.hepdesk.domain.Customer;
import com.clinistats.hepdesk.model.StaffModel;
import com.clinistats.hepdesk.request.AddStaffRequest;
import com.clinistats.hepdesk.request.UpdateStaffRequest;

@Mapper(componentModel = "spring")
public interface StaffMapper {

//	@Mapping(target = "primaryFacility", ignore = true)
	Customer toDomain(AddStaffRequest request);

//	@Mapping(target = "primaryFacility", ignore = true)
	Customer toDomain(UpdateStaffRequest request);

	StaffModel toModel(Customer provider);

	Customer toDomain(StaffModel findById);

	List<Customer> toDomains(List<StaffModel> content);

}
