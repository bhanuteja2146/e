package com.clinistats.helpdesk.dao.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PortalMessageFilter;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.TicketResponse;
import com.clinistats.helpdesk.domain.TicketResponseContent;
import com.clinistats.helpdesk.response.GetPortalMessageResponse;

public interface PortalMessageDao {

	TicketResponse getPortalMessageById(Long id);

	TicketResponse addPortalMessage(TicketResponse portalMessage);

	List<String> changePortalMsg(List<CustomObjectUpdate> domains);

	GetPortalMessageResponse getAllPortalMessage(PortalMessageFilter filter, Pagination pagination);

//	String deletePortalMessageMappedObject(DeleteMappedObject domain);

	TicketResponseContent addPortalMessageContent(TicketResponseContent content);

}
