package com.clinistats.hepdesk.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clinistats.hepdesk.config.filter.FacilityFilter;
import com.clinistats.hepdesk.config.filter.Pagination;

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
