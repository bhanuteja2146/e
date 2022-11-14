package com.clinistats.helpdesk.config.filter;


import java.util.List;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class LogUserFilter {

	List<Long> lockUserIds;
	private Status status;
}
