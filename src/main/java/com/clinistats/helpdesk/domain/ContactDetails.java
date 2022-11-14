package com.clinistats.helpdesk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDetails {
	
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
