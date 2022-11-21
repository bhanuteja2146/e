package com.clinistats.hepdesk.response;

import java.util.Date;

import com.clinistats.hepdesk.constatnts.ResponseStatus;
import com.clinistats.hepdesk.constatnts.ResponseStatusConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date timestamp;
	private String message;
	private String debugMessage;
	private String errorCode;
	private String status;
	private int statusCode;

	public ErrorDetails(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}

	public ErrorDetails(Date timestamp, String message, String errorCode) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.errorCode = errorCode;
		this.status = ResponseStatus.datanotExists.toString();
		this.statusCode=ResponseStatusConstants.dataNotExists.getValue();
		
		
	}

	public String getErrorCode() {
		return this.errorCode;
	}
	}
