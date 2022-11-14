package com.clinistats.hepdesk.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePrimaryContactRequest {

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
