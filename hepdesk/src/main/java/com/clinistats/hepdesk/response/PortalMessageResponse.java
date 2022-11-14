package com.clinistats.hepdesk.response;

import com.clinistats.hepdesk.domain.TicketResponse;

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
