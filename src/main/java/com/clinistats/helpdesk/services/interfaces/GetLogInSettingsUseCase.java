package com.clinistats.helpdesk.services.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Filter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UserLogFilter;
import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.response.GetLogInSettingsResponse;
import com.clinistats.helpdesk.response.GetUnLockLogResponse;

public interface GetLogInSettingsUseCase {

	GetLogInSettingsResponse getAllLogInSettings(Filter filter, Pagination pagination);

	LogInSettings getLogInSettingsList(Long facilityid);

	List<UserProfile> getAllLockedUser(String userName);

	Long getlimitLoginAttempt(Long facilityId);

	GetUnLockLogResponse getAllUnLockedUserLog(UserLogFilter filter, Pagination pagination);

}
