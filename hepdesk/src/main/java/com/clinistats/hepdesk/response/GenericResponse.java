package com.clinistats.hepdesk.response;


import com.clinistats.hepdesk.constatnts.ResponseStatus;
import com.clinistats.hepdesk.constatnts.ResponseStatusConstants;

public class GenericResponse<T> {

	private String message = "success";
	private String status = ResponseStatus.success.toString();
	private int statusCode = ResponseStatusConstants.success.getValue();
	private T content;

	public GenericResponse() {
	}

	public GenericResponse(String message, T content, String status, int statusCode) {
		super();
		this.message = message;
		this.content = content;
		this.status = status;
		this.statusCode = statusCode;

	}

	public GenericResponse(String message, String status, int statusCode) {
		super();
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
