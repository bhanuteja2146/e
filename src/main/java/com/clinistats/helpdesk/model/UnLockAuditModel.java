package com.clinistats.helpdesk.model;

/**
 * 
 */

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bhanu Teja
 *
 */
@Getter
@Setter
@Table(name = "EMR_UNLOCK_AUDIT")
@Entity
public class UnLockAuditModel extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "lock_Reason")
	private String lockReason;

	@Column(name = "lock_date")
	private LocalDate lockDate;

	@Column(name = "un_lock_date")
	private LocalDate unLockDate;

	private String unLockedBy;

}
