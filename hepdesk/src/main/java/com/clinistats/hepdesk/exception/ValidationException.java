package com.clinistats.hepdesk.exception;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1977204440025900433L;

	private String errorCode;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
}
