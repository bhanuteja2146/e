package com.clinistats.hepdesk.response;



import java.util.List;

import com.clinistats.hepdesk.domain.LogInSettingsLog;

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

