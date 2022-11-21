package com.clinistats.hepdesk.services.interfaces;

import java.util.List;

import com.clinistats.hepdesk.config.filter.Filter;
import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.UserLogFilter;
import com.clinistats.hepdesk.domain.LogInSettings;
import com.clinistats.hepdesk.domain.UserProfile;
import com.clinistats.hepdesk.response.GetLogInSettingsResponse;
import com.clinistats.hepdesk.response.GetUnLockLogResponse;

public interface GetLogInSettingsUseCase {

	GetLogInSettingsResponse getAllLogInSettings(Filter filter, Pagination pagination);

	LogInSettings getLogInSettingsList(Long facilityid);

	List<UserProfile> getAllLockedUser(String userName);

	Long getlimitLoginAttempt(Long facilityId);

	GetUnLockLogResponse getAllUnLockedUserLog(UserLogFilter filter, Pagination pagination);

}
