package com.clinistats.hepdesk.services.interfaces;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.StaffFilter;
import com.clinistats.hepdesk.domain.Customer;
import com.clinistats.hepdesk.response.GetStaffResponse;

public interface StaffUseCase {

	String addStaff(Customer provider);

	String updateStaff(Customer provider);

	Customer getById(String id);

	Long countByCellPhone(String cellPhone);

//	void generateStaffProvider(StaffproviderAuditLogDomain domain);
	
	GetStaffResponse getALLProviders(StaffFilter providerFilter, Pagination pagination);

	Customer delete(String ids);

}
