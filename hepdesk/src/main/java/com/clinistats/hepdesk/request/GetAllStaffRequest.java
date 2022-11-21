package com.clinistats.hepdesk.request;

import javax.validation.constraints.NotNull;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.StaffFilter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllStaffRequest {

	@NotNull
	private StaffFilter filter;

	@NotNull
	private Pagination pagination;

}
