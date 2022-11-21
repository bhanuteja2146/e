package com.clinistats.hepdesk.dao.interfaces;

import java.util.List;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PatientPortalFilter;
import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.TicketRaiseDomain;
import com.clinistats.hepdesk.response.GetPatientPortalMsgResponse;

public interface CustomerServiceDao {

	TicketRaiseDomain addPatientPortalMsg(TicketRaiseDomain patientPortalMsg);

	List<String> changePatientPortalMsg(List<CustomObjectUpdate> domains);

	GetPatientPortalMsgResponse getAllPatientPortalMsgs(PatientPortalFilter filter, Pagination pagination);

	TicketRaiseDomain getPatientPortalMsgById(Long id);
 
}
