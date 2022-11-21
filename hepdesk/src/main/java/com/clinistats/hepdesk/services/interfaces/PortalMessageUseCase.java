package com.clinistats.hepdesk.services.interfaces;

import java.util.List;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PortalMessageFilter;
import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.TicketResponse;
import com.clinistats.hepdesk.domain.TicketResponseContent;
import com.clinistats.hepdesk.response.GetPortalMessageResponse;

public interface PortalMessageUseCase {
 
	TicketResponse addPortalMessage(TicketResponse portalMessage);

	TicketResponse getPortalMessageById(Long id);

	GetPortalMessageResponse getAllPortalMessage(PortalMessageFilter filter, Pagination pagination);

	List<String> changePortalMsg(List<CustomObjectUpdate> domains);

//	String deletePortalMessageMappedObject(DeleteMappedObject domain);
	TicketResponseContent addPortalMessageContent(TicketResponseContent content);

}
