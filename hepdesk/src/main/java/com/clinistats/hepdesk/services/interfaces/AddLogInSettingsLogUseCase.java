package com.clinistats.hepdesk.services.interfaces;

import com.clinistats.hepdesk.config.filter.LogUserFilter;
import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.domain.LogInSettingsLog;
import com.clinistats.hepdesk.response.GetLogInSettingsLogResponse;

public interface AddLogInSettingsLogUseCase {



	LogInSettingsLog addLogInSettingsLog(LogInSettingsLog domain);

	GetLogInSettingsLogResponse getAllLogInSettingsLog(LogUserFilter filter, Pagination pagination);

}
