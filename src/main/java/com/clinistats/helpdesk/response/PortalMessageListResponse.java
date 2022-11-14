package com.clinistats.helpdesk.response;

import java.time.LocalDate;

import com.clinistats.helpdesk.constatnts.MailBoxType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortalMessageListResponse 
{
	private String id;
	private Object patient;
	private Object provider;
	
	private MailBoxType mailBoxType; 
	private String sentBy; 
	private LocalDate sentDate;
	private int attachments;
}
