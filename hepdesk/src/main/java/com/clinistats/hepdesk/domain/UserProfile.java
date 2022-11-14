package com.clinistats.hepdesk.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
	private long id;
	@JsonInclude(value = Include.NON_NULL)
	private String userName;
	@JsonInclude(value = Include.NON_NULL)
	private String pwd;
	@JsonInclude(value = Include.NON_NULL)
	private String firstName;
	@JsonInclude(value = Include.NON_NULL)
	private String lastName;
	@JsonInclude(value = Include.NON_NULL)
	private String emailId;
	@JsonInclude(value = Include.NON_NULL)
	private String mobile;
	@JsonInclude(value = Include.NON_NULL)
	private String userType;
	@JsonInclude(value = Include.NON_NULL)
	private Status status;
	@JsonInclude(value = Include.NON_NULL)
	private Long facilityId;
	@JsonInclude(value = Include.NON_NULL)
	private String facilityName;
	@JsonInclude(value = Include.NON_NULL)
	private Long providerId;
	@JsonInclude(value = Include.NON_NULL)
	private String providerType;
	private String lockStatus;	
	private String lockReason;
	private Date dob;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	private String  lockDate;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	private LocalDateTime  lastAccessApplication;	
	private Long limitLoginAttempt;
	private LocalDateTime  passwordChangeDate;
	private Long otp;
    private LocalDateTime otpGenerated;
    private Boolean activePatient;
	private Long portalPatientId;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	private String  unLockDate; 	
	private String unLockedBy;
    
}
