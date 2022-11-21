package com.clinistats.hepdesk.dao.interfaces;

import java.util.List;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PortalMessageFilter;
import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.TicketResponse;
import com.clinistats.hepdesk.domain.TicketResponseContent;
import com.clinistats.hepdesk.response.GetPortalMessageResponse;

public interface PortalMessageDao {

	TicketResponse getPortalMessageById(Long id);

	TicketResponse addPortalMessage(TicketResponse portalMessage);

	List<String> changePortalMsg(List<CustomObjectUpdate> domains);

	GetPortalMessageResponse getAllPortalMessage(PortalMessageFilter filter, Pagination pagination);

//	String deletePortalMessageMappedObject(DeleteMappedObject domain);

	TicketResponseContent addPortalMessageContent(TicketResponseContent content);

}
