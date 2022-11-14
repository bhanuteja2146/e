package com.clinistats.helpdesk.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
	private String id;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	String alternateContact;
	private String ext;
	private String fax;
	private String email;
	private String type;
	private String code;
	private String facilityColor;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	LocalDate startDate;
	private String facilityImageurl;
	private Boolean isPrimaryPractice;
	private Status status;

	@JsonIgnore
	private Status recordState;

	public Facility(String id) {
		super();
		this.id = id;
	}
}
