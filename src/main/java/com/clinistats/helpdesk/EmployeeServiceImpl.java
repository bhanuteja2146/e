package com.clinistats.helpdesk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeServie {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ConverterMapper converterMapper;

	@Override
	public Employee add(Employee request) {
		EmployeeModel model =	converterMapper.toEmployeeModel(request);
		EmployeeModel save = employeeRepository.save(model);
		return converterMapper.toDomain(save);
	}

	@Override
	public List<EmployeeTaxResponse> getTaxDeduction() {
		List<EmployeeModel> findAll = employeeRepository.findAll();
		return converterMapper.toDomains(findAll);
	}

}
