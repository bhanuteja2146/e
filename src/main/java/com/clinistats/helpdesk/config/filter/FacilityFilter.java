package com.clinistats.helpdesk.config.filter;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class FacilityFilter {

	private Status recordState;
	private String query;
	private String type;

}
