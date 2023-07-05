package com.clinistats.helpdesk;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ConverterMapper {

	EmployeeModel toEmployeeModel(Employee request);

	Employee toDomain(EmployeeModel save);

	List<EmployeeTaxResponse> toDomains(List<EmployeeModel> findAll);
	
	
}
