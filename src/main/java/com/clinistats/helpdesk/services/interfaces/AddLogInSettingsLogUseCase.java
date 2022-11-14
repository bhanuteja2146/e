package com.clinistats.helpdesk.services.interfaces;

import com.clinistats.helpdesk.config.filter.LogUserFilter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.response.GetLogInSettingsLogResponse;

public interface AddLogInSettingsLogUseCase {



	LogInSettingsLog addLogInSettingsLog(LogInSettingsLog domain);

	GetLogInSettingsLogResponse getAllLogInSettingsLog(LogUserFilter filter, Pagination pagination);

}
