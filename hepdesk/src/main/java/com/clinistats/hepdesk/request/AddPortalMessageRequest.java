package com.clinistats.hepdesk.request;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.context.properties.ConstructorBinding;

import com.clinistats.hepdesk.domain.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ConstructorBinding
public class AddPortalMessageRequest {
	private String id;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate createdDate;
	private LocalDate sentDate;
	private String subject;

	private String createdBy;
	private String customerId;

	private Status recordState;
	private String portalMessageContentId;
	private String textContent;

	private List<String> attachmentUrl;

}
