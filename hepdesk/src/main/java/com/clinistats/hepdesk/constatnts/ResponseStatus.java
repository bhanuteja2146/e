package com.clinistats.hepdesk.constatnts;

public enum ResponseStatus {

	success("saved Successfully!"), update("updated Successfully!"),delete("deleted Successfully!"), dataExists("data already exists!"),
	datanotExists("data not exists!"), dataInvalid("Invalid data!"),
	error("failed!");

	private String status;

	ResponseStatus(String status) {
		this.status = status;
	}

	public String Status() {
		return status;
	}
}
