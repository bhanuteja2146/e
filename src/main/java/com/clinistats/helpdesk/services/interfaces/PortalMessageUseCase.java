package com.clinistats.helpdesk.services.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PortalMessageFilter;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.TicketResponse;
import com.clinistats.helpdesk.domain.TicketResponseContent;
import com.clinistats.helpdesk.response.GetPortalMessageResponse;

public interface PortalMessageUseCase {
 
	TicketResponse addPortalMessage(TicketResponse portalMessage);

	TicketResponse getPortalMessageById(Long id);

	GetPortalMessageResponse getAllPortalMessage(PortalMessageFilter filter, Pagination pagination);

	List<String> changePortalMsg(List<CustomObjectUpdate> domains);

//	String deletePortalMessageMappedObject(DeleteMappedObject domain);
	TicketResponseContent addPortalMessageContent(TicketResponseContent content);

}
