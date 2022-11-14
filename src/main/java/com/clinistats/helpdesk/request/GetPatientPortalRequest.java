package com.clinistats.helpdesk.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.PatientPortalFilter;

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
