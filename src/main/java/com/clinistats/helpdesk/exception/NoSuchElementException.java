package com.clinistats.helpdesk.exception;

             
public class NoSuchElementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1977204440025900433L;
	
	private String errorCode;
	
	public NoSuchElementException(String message) {
		super(message);
	}

	public NoSuchElementException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}

	
}
