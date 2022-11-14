package com.clinistats.helpdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.StaffFilter;
import com.clinistats.helpdesk.dao.interfaces.StaffDao;
import com.clinistats.helpdesk.domain.Customer;
import com.clinistats.helpdesk.response.GetStaffResponse;
import com.clinistats.helpdesk.services.interfaces.StaffUseCase;

@Service
public class StaffUseCaseImpl implements StaffUseCase {

	@Autowired
	private StaffDao providerDao;

	@Override
	public String addStaff(Customer provider) {
		return providerDao.addStaff(provider);
	}

	@Override
	public String updateStaff(Customer provider) {
		Customer model = providerDao.getById(provider.getId().toString());
		if (model != null) {
			return providerDao.updateStaff(provider);
		}
		return null;
	}

	@Override
	public Customer getById(String id) {
		return providerDao.getById(id);
	}

	@Override
	public Long countByCellPhone(String cellPhone) {
		return providerDao.countByCellPhone(cellPhone);
	}

	@Override
	public GetStaffResponse getALLProviders(StaffFilter providerFilter, Pagination pagination) {
		return providerDao.getAll(providerFilter, pagination);
	}

	@Override
	public Customer delete(String ids) {

		return providerDao.delete(ids);
	}

}
