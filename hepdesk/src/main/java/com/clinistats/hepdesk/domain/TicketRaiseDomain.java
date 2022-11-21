package com.clinistats.hepdesk.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.clinistats.hepdesk.constatnts.MailBoxType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRaiseDomain {

	private String id;
//	private String  patientId;
	private String createdBy;
	private String sentBy;
	private String providerId;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate sentDate;

	private String subject;
	private MailBoxType mailBoxType;
	private List<String> attachmentUrl;

	// for Attachments
	private List<TicketResponseContent> patientPortalMsgContentList = new ArrayList<>(0);

	private Status recordState;

}
