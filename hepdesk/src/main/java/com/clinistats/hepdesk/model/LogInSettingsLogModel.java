package com.clinistats.hepdesk.model;

/**
 * 
 */

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clinistats.hepdesk.domain.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * @author giri
 *
 */

@Getter
@Setter
@Entity
@Table(name="log_in_settings_log")

public class LogInSettingsLogModel extends AuditModel{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "lock_user_id")	
	private Long  lockUserId;
	
	@Column(name = "facility_id")	
	private Long  facilityId;
	
	@Column(name = "unlocking_user_id")	
	private Long  unLockingUserId;
	
	@Column(name = "lock_Reason")
	private String lockReason;
	
	@Column(name = "lock_date")
	private LocalDateTime  lockDate;
	
	@Column(name = "unlock_date")
	private LocalDateTime  unLockDate;
	
	@Column(name = "old_passwrd")
	private String oldPasswrd;
	
	
	@Column(name = "user_id")	
	private Long  userId;
		
	@Column(name = "record_state")
	private Status recordState;
	
	
	
}
