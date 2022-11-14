package com.clinistats.helpdesk.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrimaryContactRequest {
	
	private String address1;

	private String address2;

	private String city;

	private String state;

	private String zip;

	private String country;

	private String directions;
	
	private String region;
	
	
}
