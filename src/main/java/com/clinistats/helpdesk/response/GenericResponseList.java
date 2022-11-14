package com.clinistats.helpdesk.response;

import com.clinistats.helpdesk.constatnts.ResponseStatus;
import com.clinistats.helpdesk.constatnts.ResponseStatusConstants;

public class GenericResponseList<T> {

	private String message = "success";
	private String status = ResponseStatus.success.toString();
	private int statusCode = ResponseStatusConstants.success.getValue();
	private T content;
	private long totalElements;

	public GenericResponseList() {
	}

	public GenericResponseList(String message, T content, String status, int statusCode) {
		super();
		this.message = message;
		this.content = content;
		this.status = status;
		this.statusCode = statusCode;
		this.totalElements = 0;
	}

	public GenericResponseList(String message, String status, int statusCode) {
		super();
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
		this.totalElements = 0;
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

	public long getTotalElements() {
		return this.totalElements;
	}

	public void setTotalElements(long total) {
		this.totalElements = total;
	}

}
