package com.clinistats.helpdesk.config.filter;


import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class UserFilter {

	private String query;
	private Status status;
	private Long faciltiyId;
	private String userType;
}
