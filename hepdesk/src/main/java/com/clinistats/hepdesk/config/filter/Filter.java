package com.clinistats.hepdesk.config.filter;


import com.clinistats.hepdesk.domain.Status;

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
