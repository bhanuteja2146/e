package com.clinistats.helpdesk.response;


import java.util.List;

import com.clinistats.helpdesk.domain.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilePageable {

	private long totalRecords;
	private List<UserProfile> templateSoaps;


}