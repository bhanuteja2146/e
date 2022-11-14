package com.clinistats.helpdesk.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.clinistats.helpdesk.domain.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author swapna
 * 
 * @Date 13/05/2020.
 * 
 */

@Entity
@Table(name = "Staff")

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffModel extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staff_id")
	private Long id;

	@Formula(value = "concat(FIRST_NAME,' ', MIDDLE_NAME,' ', LAST_NAME)")
	private String name;

	@Column(name = "NPI")
	private String npi;

	@Column(name = "PROVIDER_GROUP_NPI")
	private Long providerGroupNpi;

	@Column(name = "TAXONOMY_CODE")
	private String taxonomyCode;

	@Column(name = "SPECILITY")
	private String specility;

	@Column(name = "EMCD_PROVIDER_ID")
	private String emcdProviderId;

	@Column(name = "PROVIDER_INITIALS")
	private String providerInitials;

	@Column(name = "UPIN")
	private String upin;

	@Column(name = "DEA_NUMBER")
	private String deaNumber;

	@Column(name = "DeACTIVE_STATUS")
	private Status deActiveStatus;

	@Column(name = "DEA_ACTIVE_DATE")
	private Date deaActiveDate;

	@Column(name = "DEA_TERM_DATE")
	private Date deaTermDate;

	@Column(name = "PROVIDER_SITE_ID")
	private String providerSiteId;

	@Column(name = "LANGUAGE_SPOKEN")
	private String languageSpoken;

	@Column(name = "NETWORK_AFFILIATION")
	private String networkAffiliation;

	@Column(name = "RESIDENT")
	private String resident;

	@Column(name = "SPECIALITY_LICENSE")
	private String specialityLicense;

	@Column(name = "STATE_LICENSE")
	private String stateLicense;

	@Column(name = "LICENSE_ACTIVE_DATE")
	private Date licenseActiveDate;

	@Column(name = "LICENSE_TERM_DATE")
	private Date licenseTermDate;

	@Column(name = "PRESCRIPTION_AUTH_NUMBER")
	private String prescriptionAuthNumber;

	@Column(name = "TAX_ID_TYPE")
	private String taxIdType;

	@Column(name = "PROVIDER_TAX_ID")
	private String providerTaxId;

	@Column(name = "ORGANIZATION_TYPE")
	private String organizationType;

	@Column(name = "TAX_ID_SUFFIX")
	private String taxIdSuffix;

	@Column(name = "MEDICARE_GRP_NUMBER")
	private String medicareGrpNumber;

	@Column(name = "MEDICAID_GRP_NUMBER")
	private String medicaidGrpNumber;

	@Column(name = "IS_CARE_MANAGER")
	private Boolean isCareManager;

	@ManyToOne
	@JoinColumn(name = "FacilityModel_facilityId")
	private FacilityModel primaryFacility;

	@Column(name = "ERX_STATUS")
	private String erxStatus;

	@Column(name = "EPS_STATUS")
	private String epsStatus;

	@Column(name = "P2P_STATUS")
	private String p2pStatus;

	@Column(name = "EMR_MOBILE_STATUS")
	private String emrMobileStatus;

	@Column(name = "PRINT_NAME")
	private String printName;

	@Column(name = "BILLING_FACILITY")
	private String billingFacility;

	@Column(name = "CITY")
	private String city;

	/*
	 * @Column(name = "CONFIRM_PASSWORD") private String confirmPassword;
	 */
	@Column(name = "DEGREES")
	private String degrees;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "EMPLOYEE_ID")
	private String employerId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	/*
	 * @Column(name = "PASSWORD") private String password;
	 */
	@Column(name = "PREFIX")
	private String prefix;

	@Column(name = "PRIMARY_SERVICE_LOCATION")
	private String primaryServiceLocation;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "RECORD_STATE")
	private Status recordState;

	@Column(name = "PHOTO_PATH")
	private String photoPath;

	@Column(name = "SIGN_PHOTO_PATH")
	private String signPhotoPath;

	@Column(name = "GLOBAL_STATUS", columnDefinition = "boolean default true")
	private boolean globalStatus;

	@Column(name = "Default_Screen", columnDefinition = "varchar(255) default 'Quick links'")
	private String defaultScreen;

	@Column(name = "number__of_slot", columnDefinition = "integer default 4")
	private Integer numberOfSlot;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTACT_DETAILS_ID")
	private ContactDetailsModel contactDetails;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PRIMARY_CONTACT_DETAILS_ID")
	private PrimaryContactDetailsModel addressInfo;

	// NEW COLUMN
	private boolean providerOrNonProvider;
	private Boolean resource;
	private Long userProfileId;

}
