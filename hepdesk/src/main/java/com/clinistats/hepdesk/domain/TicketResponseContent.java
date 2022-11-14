package com.clinistats.hepdesk.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseContent {
	private String id;
	private String customerId;
	private LocalDate createdDate;
	private String createdBy;
	private String content;
	private boolean template;
	private String templateName;
	private Status recordState;
}
