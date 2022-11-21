package com.clinistats.hepdesk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PortalMessageFilter;
import com.clinistats.hepdesk.dao.interfaces.PortalMessageDao;
import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.TicketResponse;
import com.clinistats.hepdesk.domain.TicketResponseContent;
import com.clinistats.hepdesk.response.GetPortalMessageResponse;
import com.clinistats.hepdesk.services.interfaces.PortalMessageUseCase;

@Service
public class PortalMessageUseCaseImpl implements PortalMessageUseCase 
{
	
	@Autowired
	private PortalMessageDao portalMessageDao;

	@Override
	public TicketResponse addPortalMessage(TicketResponse portalMessage) 
	{
	 
		return portalMessageDao.addPortalMessage(portalMessage);
	}
	@Override
	public TicketResponse getPortalMessageById(Long id) 
	{	 
		return portalMessageDao.getPortalMessageById(id);
	}

	@Override
	public GetPortalMessageResponse getAllPortalMessage(PortalMessageFilter filter, Pagination pagination)
	{
	 
		return portalMessageDao.getAllPortalMessage(filter, pagination);
	}
	
	@Override
	public List<String> changePortalMsg(List<CustomObjectUpdate> domains) 
	{
		 
		return portalMessageDao.changePortalMsg(domains);
	}
	@Override
	public TicketResponseContent addPortalMessageContent(TicketResponseContent content) {
		// TODO Auto-generated method stub
		return portalMessageDao.addPortalMessageContent(content);
	}
}
