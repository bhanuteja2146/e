package com.clinistats.hepdesk.services.interfaces;

import java.util.List;

import com.clinistats.hepdesk.domain.LogInSettings;

public interface AddLogInSettingsUseCase {

	List<LogInSettings> addLogInSettings(List<LogInSettings> domain);

}
