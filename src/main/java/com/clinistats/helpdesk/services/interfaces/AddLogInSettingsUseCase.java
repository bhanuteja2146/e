package com.clinistats.helpdesk.services.interfaces;

import java.util.List;

import com.clinistats.helpdesk.domain.LogInSettings;

public interface AddLogInSettingsUseCase {

	List<LogInSettings> addLogInSettings(List<LogInSettings> domain);

}
