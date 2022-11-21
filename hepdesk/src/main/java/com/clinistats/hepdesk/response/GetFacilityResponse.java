package com.clinistats.hepdesk.response;

import java.util.List;

import com.clinistats.hepdesk.domain.Facility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetFacilityResponse {

	private List<Facility> facility;
	private Long totalRecords;
}