package com.clinistats.hepdesk.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteMappedObjectRequest 
{
	@NotNull @NotEmpty
	private Long portalMessageId;

	@NotNull @NotEmpty
	private Long objectId;
	
//	private MappedObjectType objectType;
}
