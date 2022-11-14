package com.clinistats.helpdesk.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PatientPortalMsgTemplate {
	private long id;
	private String content;
	private LocalDate createdDate; // sent date
	private String createdBy; // sent by
	private String updatedBy;
	private String templateName;
	private boolean template;

}
