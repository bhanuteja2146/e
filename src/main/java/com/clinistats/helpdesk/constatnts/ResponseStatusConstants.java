package com.clinistats.helpdesk.constatnts;

public enum ResponseStatusConstants {

	success(111), update(112), dataNotExists(113), error(114), dataInvalid(115),blocked(116);

	private final int statusCode;

	ResponseStatusConstants(final int newValue) {
		statusCode = newValue;
	}

	public int getValue() {
		return statusCode;
	}
}
