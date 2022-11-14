package com.clinistats.helpdesk.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PortalMessageFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPortalMessageRequest 
{
	@NotNull
	@Valid
	private PortalMessageFilter portalMessageFilter;
	@Valid
	@NotNull
	private Pagination pagination;
}
