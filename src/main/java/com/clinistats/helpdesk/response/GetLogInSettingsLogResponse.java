package com.clinistats.helpdesk.response;



import java.util.List;

import com.clinistats.helpdesk.domain.LogInSettingsLog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetLogInSettingsLogResponse {

	private long totalRecords;
	private List<LogInSettingsLog> loginSettingLogs;


}

