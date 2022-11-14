/**
 * 
 */
package com.clinistats.helpdesk.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lukman.d
 *
 */
@Getter
@Setter
@Table(name = "USER_PROFILE")
@Entity
public class UserProfileModel extends AuditModel implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "USER_NAME", unique = true)
	private String userName;

	@Column(name = "passwrd")
	private String pwd;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "USER_TYPE")
	private String userType;

	@Enumerated(EnumType.STRING)
	@Column(name = "USER_STATUS")
	private Status status;

	@Column(name = "FACILITY_ID")
	private Long facilityId;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "PROVIDER_ID")
	private Long providerId;

	@Column(name = "PROVIDER_TYPE")
	private String providerType;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "OTP")
	private Long otp;

	@Column(name = "OTP_CREATED_TIME")
	private LocalDateTime otpGenerated;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "PATIENT_ACTIVE")
	private Boolean activePatient;

	@Override
	public String getPassword() {
		return pwd;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Column(name = "lock_status")
	private String lockStatus;

	@Column(name = "lock_Reason")
	private String lockReason;

	@Column(name = "lock_date")
	private String lockDate;

	private String unLockDate;
	private String unLockedBy;

	@Column(name = "last_access_application")
	private LocalDateTime lastAccessApplication;

	@Column(name = "limit_login_attempt")
	private Long limitLoginAttempt;

	@Column(name = "password_change_date")
	private LocalDateTime passwordChangeDate;

	@Column(name = "PORTAL_PATIENT_ID")
	private Long portalPatientId;

}
