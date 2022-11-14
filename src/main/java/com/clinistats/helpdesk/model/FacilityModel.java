package com.clinistats.helpdesk.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="FACILITY")
@Getter
@Setter
@ToString
public class FacilityModel  extends AuditModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	///private static final long serialVersionUID = -6669591925021741270L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	//basic info	
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ADDRESS1")
	private String address1;	
	
	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "COUNTRY")
	private String country;

	//contact details
	@Column(name = "PHONE")
	private String phone;

	@Column(name = "ALTERNATE_CONTACT")
	String alternateContact;

	@Column(name = "EXT")
	private String ext;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "CODE")
	private String code;

	@Column(name = "FACILITY_COLOR")
	private String facilityColor;

	@Column(name = "START_DATE")
	LocalDate startDate;

	@Column(name = "IS_PRIMARY_PRACTICE")
	private Boolean isPrimaryPractice;

	@Column(name="STATUS")
    private Status status;
	
	@Column(name = "FACILITY_IMAGE_URL")
	private String facilityImageurl;
    
	@Column(name="RECORD_STATE")
	private Status recordState;
	
		
}
