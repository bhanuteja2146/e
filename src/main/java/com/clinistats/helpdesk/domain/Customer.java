package com.clinistats.helpdesk.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

	@JsonProperty("id")
	private String id;

	private String name;

	private String npi;

	private Long providerGroupNpi;

	private String taxonomyCode;

	private String specility;

	private String emcdProviderId;

	private String providerInitials;

	private String upin;

	private String deaNumber;

	private Status deActiveStatus;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private Date deaActiveDate;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private Date deaTermDate;

	private String providerSiteId;

	private String languageSpoken;

	private String networkAffiliation;

	private String resident;

	private String specialityLicense;

	private String stateLicense;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private Date licenseActiveDate;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private Date licenseTermDate;

	private String prescriptionAuthNumber;

	private String taxIdType;

	private String providerTaxId;

	private String organizationType;

	private String taxIdSuffix;

	private String medicareGrpNumber;

	private String medicaidGrpNumber;

	private Boolean isCareManager;

	private String erxStatus;

	private String epsStatus;

	private String p2pStatus;

	private String emrMobileStatus;

	private String printName;

	private String billingFacility;

	// private String confirmPassword;

	private String degrees;

	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private Date dob;

	private String employerId;

	private String firstName;

	private String lastName;

	private String middleName;

	// private String password;

	private String prefix;

	private String primaryServiceLocation;

	private String sex;

	private String ssn;

	private String suffix;

	private String username;

	private Status recordState;

//	private Facility primaryFacility;

	private Long userProfileId;

	private ContactDetails contactDetails;

	private PrimaryContactDetails addressInfo;

	private String photoPath;

	private String signPhotoPath;

	private boolean globalStatus;

	private String defaultScreen;

	private Integer numberOfSlot;

	// NEW FILED
	private boolean providerOrNonProvider;

	private Boolean resource;

	public Customer(String id) {
		super();
		this.id = id;
	}

}
