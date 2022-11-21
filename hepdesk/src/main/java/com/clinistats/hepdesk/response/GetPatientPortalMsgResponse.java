package com.clinistats.hepdesk.response;

import java.util.List;

import com.clinistats.hepdesk.domain.TicketRaiseDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPatientPortalMsgResponse 
{
	private List<TicketRaiseDomain> patientPortalMsgs;
	private Long totalRecords;
}
