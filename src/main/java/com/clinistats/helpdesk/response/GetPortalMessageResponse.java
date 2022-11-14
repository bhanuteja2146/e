package com.clinistats.helpdesk.response;

import java.util.List;

import com.clinistats.helpdesk.domain.TicketResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPortalMessageResponse 
{
	private List<TicketResponse> portalMessages;
	private Long totalRecords;
}
