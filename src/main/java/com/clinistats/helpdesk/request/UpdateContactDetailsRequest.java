package com.clinistats.helpdesk.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateContactDetailsRequest {

	private String id;
	private String homePhone;
	private String cellPhone;
	private String email;
	private Boolean isEmail;
	private String reason; 
	private String workPhone;
	private String fax;
	private String pager;
}
