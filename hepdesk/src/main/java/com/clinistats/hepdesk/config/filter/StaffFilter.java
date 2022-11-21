package com.clinistats.hepdesk.config.filter;

import java.util.List;

import com.clinistats.hepdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class StaffFilter {

	private Status recordState;
	private String query;
	private List<Long> facilityId;
	private List<Long> providerIds;
	private List<Long> nonProviderIds;

}
