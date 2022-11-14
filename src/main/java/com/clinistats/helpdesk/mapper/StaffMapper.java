package com.clinistats.helpdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.clinistats.helpdesk.domain.Customer;
import com.clinistats.helpdesk.model.StaffModel;
import com.clinistats.helpdesk.request.AddStaffRequest;
import com.clinistats.helpdesk.request.UpdateStaffRequest;

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
