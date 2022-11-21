package com.clinistats.hepdesk.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.PatientPortalFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPatientPortalRequest 
{
	@NotNull
	@Valid
	private PatientPortalFilter patientPortalFilter;
	@Valid
	@NotNull
	private Pagination pagination;
}
