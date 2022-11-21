package com.clinistats.hepdesk.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {

	@NotNull
	private String userName;
	private Long providerId;
}
