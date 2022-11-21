package com.clinistats.hepdesk.response;

/**
 * 
 */

import java.util.List;

import com.clinistats.hepdesk.domain.LogInSettings;

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