package com.clinistats.helpdesk.dao.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PatientPortalFilter;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.TicketRaiseDomain;
import com.clinistats.helpdesk.response.GetPatientPortalMsgResponse;

public interface CustomerServiceDao {

	TicketRaiseDomain addPatientPortalMsg(TicketRaiseDomain patientPortalMsg);

	List<String> changePatientPortalMsg(List<CustomObjectUpdate> domains);

	GetPatientPortalMsgResponse getAllPatientPortalMsgs(PatientPortalFilter filter, Pagination pagination);

	TicketRaiseDomain getPatientPortalMsgById(Long id);
 
}
