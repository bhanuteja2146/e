package com.clinistats.helpdesk.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name="ticket_content")
public class TicketContentModel extends AuditModel 
{ 
	 
	private static final long serialVersionUID = -1815380446370684325L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "created_date")
	private LocalDate createdDate;    
	
	@Column(name = "created_by")
	private String createdBy;  	
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "template")
	private boolean template;	 
	
	@Column(name = "template_name")
	private String templateName;
	
	@Column(name="record_state")
	private Status recordState;
}
