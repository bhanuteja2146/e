package com.clinistats.helpdesk.response;

/**
 * 
 */

import java.util.List;

import com.clinistats.helpdesk.domain.LogInSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author giri
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetLogInSettingsResponse {

	private long totalRecords;
	private List<LogInSettings> logInSettings;

}