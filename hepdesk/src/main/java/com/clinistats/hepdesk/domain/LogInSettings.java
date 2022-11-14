package com.clinistats.hepdesk.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInSettings {
	
	private Long id;
	private Long facilityId;
	private String facilityName;
	private Long passMinLength;
	private Long passMaxLength;
	private Long passExpireDays;
	private Long passExpireAlertDays;
	private Long limitLastPassUsed;
	private Long limitSessionTimeout;
	private Long limitLoginAttempt;
	private Long limitLastApplicationUsed;
	private Status recordState;
	
}
