package com.clinistats.helpdesk.config.filter;

import java.util.List;

import com.clinistats.helpdesk.constatnts.MailBoxType;
import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class PatientPortalFilter {
	private MailBoxType mailBoxType;
	private String query;
	private String patientId;
	private Status recordState;
	private List<String> providerIds;
}
