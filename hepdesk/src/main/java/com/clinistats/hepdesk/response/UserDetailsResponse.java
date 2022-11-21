package com.clinistats.hepdesk.response;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lukman.d
 *
 */
@Getter
@Setter
@Builder
public class UserDetailsResponse {
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private long userId;
	private Map permissionsMap;
	private boolean provider;
	private String displayPicture;
	private Object navigations;
	private String providerType;
	private Long providerId;
	private String userType;
	private String facilityName;
	private Long portalPatientId;	
	
}
