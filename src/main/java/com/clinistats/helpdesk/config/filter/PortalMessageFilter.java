package com.clinistats.helpdesk.config.filter;

import com.clinistats.helpdesk.constatnts.MailBoxType;
import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class PortalMessageFilter 
{
	private MailBoxType mailBoxType;
	private String query; 
	private String providerId;
	private String patientId;
	private Status recordState;
}
