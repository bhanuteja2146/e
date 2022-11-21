package com.clinistats.hepdesk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrimaryContactDetails {
	
	private String id;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String zip;
	
	private String country;

	private String directions;
	
	private String region;
	
}
