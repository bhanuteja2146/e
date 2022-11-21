package com.clinistats.hepdesk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lukman.d
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderFacilityPermssionMappingDomain {
	private Long id;
	private Long facilityId;
	private Long providerId;
	private String userName;
	private String createdBy;
	private Status status;
	private boolean resource;
}
