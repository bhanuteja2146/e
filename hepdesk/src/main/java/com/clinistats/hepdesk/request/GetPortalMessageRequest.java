package com.clinistats.hepdesk.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PortalMessageFilter;

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
