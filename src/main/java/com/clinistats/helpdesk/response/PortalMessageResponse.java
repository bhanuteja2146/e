package com.clinistats.helpdesk.response;

import com.clinistats.helpdesk.domain.TicketResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortalMessageResponse {
	private String id;
	private Object patient;
	private String subject;

//	private List<LabResnse> portalMessageLabResponseLst;
//	private List<DiRespoponse> portalMessageDiResponseLst;
	private TicketResponse portalMessage;
}
