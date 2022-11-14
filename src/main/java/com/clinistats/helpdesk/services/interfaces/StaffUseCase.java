package com.clinistats.helpdesk.services.interfaces;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.StaffFilter;
import com.clinistats.helpdesk.domain.Customer;
import com.clinistats.helpdesk.response.GetStaffResponse;

public interface StaffUseCase {

	String addStaff(Customer provider);

	String updateStaff(Customer provider);

	Customer getById(String id);

	Long countByCellPhone(String cellPhone);

//	void generateStaffProvider(StaffproviderAuditLogDomain domain);
	
	GetStaffResponse getALLProviders(StaffFilter providerFilter, Pagination pagination);

	Customer delete(String ids);

}
