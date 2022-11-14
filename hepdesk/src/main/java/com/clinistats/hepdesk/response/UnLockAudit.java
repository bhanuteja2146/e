package com.clinistats.hepdesk.response;

/**
 * 
 */

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bhanu Teja
 *
 */
@Getter
@Setter
public class UnLockAudit {


	private long id;

	private String userName;

	
	private String lockReason;
	
    @JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate  lockDate; 	
	
    @JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate  unLockDate; 	
	
	private String unLockedBy;

	
}
