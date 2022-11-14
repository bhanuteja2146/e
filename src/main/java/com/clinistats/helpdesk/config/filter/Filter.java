package com.clinistats.helpdesk.config.filter;


import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class Filter 
{
	private String query;
	private Status recordState;
	
}
