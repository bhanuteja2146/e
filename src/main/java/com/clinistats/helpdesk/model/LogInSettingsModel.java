package com.clinistats.helpdesk.model;

/**
 * 
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clinistats.helpdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * @author giri
 *
 */

@Getter
@Setter
@Entity
@Table(name = "LogIn_Settings")

public class LogInSettingsModel extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "facility_id")
	private Long facilityId;

	@Column(name = "facility_name")
	private String facilityName;

	@Column(name = "pass_min_length")
	private Long passMinLength;

	@Column(name = "pass_max_length")
	private Long passMaxLength;

	@Column(name = "pass_expire_days")
	private Long passExpireDays;

	@Column(name = "pass_expire_alert_days")
	private Long passExpireAlertDays;

	@Column(name = "limit_last_pass_used")
	private Long limitLastPassUsed;

	@Column(name = "limit_session_timeout")
	private Long limitSessionTimeout;

	@Column(name = "limit_login_attempt")
	private Long limitLoginAttempt;

	@Column(name = "limit_last_application_used")
	private Long limitLastApplicationUsed;

	@Column(name = "record_state")
	private Status recordState;

}
