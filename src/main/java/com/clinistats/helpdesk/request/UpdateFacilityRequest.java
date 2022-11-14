package com.clinistats.helpdesk.request;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.clinistats.helpdesk.domain.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFacilityRequest {
	

	private String id;	
	@NotNull
	@NotEmpty
	@Size(min=1,max=50)
	private String name;	
	@Size(min=0,max=50)
	private String address1;
	@Size(min=0,max=50)
	private String address2;
	@Size(min=0,max=50)
	private String city;
	@Size(min=0,max=50)
	private String state;
	@Size(min=0,max=10)
	private String zip;
	private String country;
	@Size(min=0,max=12)
	private String phone;
	@Size(min=0,max=12)
	String alternateContact;
	@Size(min=0,max=12)
	private String ext;
	@Size(min=0,max=12)	
	private String fax;
	@Email(message = "Email should be valid")
	private String email;
	private String type;
	private String code;
	private String facilityColor;
	private Boolean isPrimaryPractice;
	
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy")
	private LocalDate startDate;
	private Status status;
}
