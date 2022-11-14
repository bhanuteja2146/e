package com.clinistats.hepdesk.config.filter;

import java.util.List;

import com.clinistats.hepdesk.constatnts.MailBoxType;
import com.clinistats.hepdesk.domain.Status;

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
