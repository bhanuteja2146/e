package com.clinistats.helpdesk.services.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PatientPortalFilter;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.TicketRaiseDomain;
import com.clinistats.helpdesk.response.GetPatientPortalMsgResponse;

public interface CustomerServiceUseCase {

	TicketRaiseDomain addPatientPortalMsg(TicketRaiseDomain portalMessage);
	TicketRaiseDomain getPatientPortalMsgById(Long id);
	List<String> changePatientPortalMsg(List<CustomObjectUpdate> domains);
	GetPatientPortalMsgResponse getAllPatientPortalMsgs(PatientPortalFilter filter, Pagination pagination);


}
