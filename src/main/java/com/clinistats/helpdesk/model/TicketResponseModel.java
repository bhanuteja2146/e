package com.clinistats.helpdesk.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.clinistats.helpdesk.constatnts.MailBoxType;
import com.clinistats.helpdesk.domain.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ticket_response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketResponseModel extends AuditModel {

	private static final long serialVersionUID = 3060097241020859464L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//
//	@Column(name = "patient_id")
//	private String patientId;

	@Column(name = "customerId")
	private String customerId;

	@Column(name = "sent_by")
	private String sentBy;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "sent_date")
	private LocalDate sentDate;

	@Column(name = "subject")
	private String subject;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ticket_response_content_mapping", joinColumns = @JoinColumn(name = "PortalMessageModel_id"), inverseJoinColumns = @JoinColumn(name = "PortalMessageContentModel_id"))
	private java.util.List<TicketResponseContentModel> portalMessageContentList = new ArrayList<>(0);

	private MailBoxType mailBoxType;

	@ElementCollection
	@Column(name = "attachment_url")
	private List<String> attachmentUrl;

	private Status recordState;
}
