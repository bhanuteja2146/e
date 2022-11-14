package com.clinistats.helpdesk.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.clinistats.helpdesk.constatnts.MailBoxType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponse {
	private String id;
//	private String patientId;
	private String customerId;
	private String createdBy;
	private String sentBy;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate sentDate;

	private String subject;
	private MailBoxType mailBoxType;

	// for Attachments
	private List<String> attachmentUrl;
	private List<TicketResponseContent> portalMessageContentList = new ArrayList<>(0);

	private Status recordState;

}
