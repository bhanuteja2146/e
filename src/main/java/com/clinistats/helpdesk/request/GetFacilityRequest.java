package com.clinistats.helpdesk.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clinistats.helpdesk.config.filter.FacilityFilter;
import com.clinistats.helpdesk.config.filter.Pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFacilityRequest {

	private FacilityFilter filter;
	@Valid
	@NotNull
	private Pagination pagination;
	
}
