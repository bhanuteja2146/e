package com.clinistats.helpdesk;

import java.util.List;

public interface EmployeeServie {

	Employee add(Employee request);

	List<EmployeeTaxResponse> getTaxDeduction();

}
