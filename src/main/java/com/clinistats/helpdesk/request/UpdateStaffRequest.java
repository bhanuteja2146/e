package com.clinistats.helpdesk.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.clinistats.helpdesk.domain.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateStaffRequest {

	private String id;

	/* private String name; */

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

//	private String primaryFacility;
//
//	private String primaryFacilityName;
	
	private String erxStatus;

	private String epsStatus;

	private String p2pStatus;

	private String emrMobileStatus;

	private String printName;

	private String billingFacility;

	//@NotEmpty
	//@Size(min=1,max=50)
	//@ApiModelProperty(required = true)
	//private String confirmPassword="";

	private String degrees;
	

	@NotNull
	@ApiModelProperty(required = true)
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private Date dob;

	private String email;

	private String employerId;

	@NotEmpty
	@Size(min=1,max=50)
	@ApiModelProperty(required = true)
	private String firstName;

	@NotEmpty
	@Size(min=1,max=50)
	@ApiModelProperty(required = true)
	private String lastName;

	private String middleName;

	//@NotEmpty
	//@Size(min=1,max=50)
	//@ApiModelProperty(required = true)
	//private String password;

	private String prefix;

	private String primaryServiceLocation;

	private String sex;

	private String ssn;

	private String state;

	private String suffix;

	@NotEmpty
	@Size(min=1,max=50)
	@ApiModelProperty(required = true)
	private String username;
	
	private String userProfileId;

	private UpdateContactDetailsRequest contactDetails;

	private UpdatePrimaryContactRequest addressInfo;
	
	private String photoPath;
	
	private boolean globalStatus;

	private String defaultScreen;
	
	private Integer  numberOfSlot;


}
