package com.clinistats.hepdesk.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInSettingsLog {
	
	private Long id;
	private Long lockUserId;
	private Long facilityId;
	private Long unLockingUserId;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	private LocalDateTime  lockDate;
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	private LocalDateTime  unLockDate;
	private String lockReason;
	private Status recordState;
	private String oldPasswrd;
	
	private String facilityName;
	private String lockUserName;
	private String unLockUserName;
	
}
