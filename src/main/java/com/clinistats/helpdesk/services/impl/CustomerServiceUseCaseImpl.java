package com.clinistats.helpdesk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PatientPortalFilter;
import com.clinistats.helpdesk.dao.interfaces.CustomerServiceDao;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.TicketRaiseDomain;
import com.clinistats.helpdesk.response.GetPatientPortalMsgResponse;
import com.clinistats.helpdesk.services.interfaces.CustomerServiceUseCase;

@Service
public class CustomerServiceUseCaseImpl implements CustomerServiceUseCase {

	@Autowired
	private CustomerServiceDao patientPortalMsgDao;

	@Override
	public TicketRaiseDomain addPatientPortalMsg(TicketRaiseDomain patientPortalMsg) {
		return patientPortalMsgDao.addPatientPortalMsg(patientPortalMsg);
	}

	@Override
	public TicketRaiseDomain getPatientPortalMsgById(Long id) {
		// TODO Auto-generated method stub
		return patientPortalMsgDao.getPatientPortalMsgById(id);
	}

	@Override
	public List<String> changePatientPortalMsg(List<CustomObjectUpdate> domains) {
		// TODO Auto-generated method stub
		return patientPortalMsgDao.changePatientPortalMsg(domains);
	}

	@Override
	public GetPatientPortalMsgResponse getAllPatientPortalMsgs(PatientPortalFilter filter, Pagination pagination) {
		// TODO Auto-generated method stub
		return patientPortalMsgDao.getAllPatientPortalMsgs(filter, pagination);
	}

}
