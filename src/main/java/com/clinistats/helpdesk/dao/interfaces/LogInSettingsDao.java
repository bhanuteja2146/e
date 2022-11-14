package com.clinistats.helpdesk.dao.interfaces;

/**
 * 
 */

import java.util.List;

import com.clinistats.helpdesk.config.filter.Filter;
import com.clinistats.helpdesk.config.filter.LogUserFilter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UserLogFilter;
import com.clinistats.helpdesk.domain.CustomObjectUpdate;
import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.response.GetLogInSettingsLogResponse;
import com.clinistats.helpdesk.response.GetLogInSettingsResponse;
import com.clinistats.helpdesk.response.GetUnLockLogResponse;

/**
 * @author giri
 *
 */

public interface LogInSettingsDao {

	List<LogInSettings> addLogInSettings(List<LogInSettings> domain);

	LogInSettingsLog addLogInSettingsLog(LogInSettingsLog domain);

	List<LogInSettings> updateLogInSettings(List<LogInSettings> domainsUpdate);

	List<String> changeUserProfileStatus(List<CustomObjectUpdate> customDomain);

	GetLogInSettingsResponse getAllLogInSettings(Filter filter, Pagination pagination);

	LogInSettings getLogInSettingsList(Long facilityId);

	int unLockByLockedUserId(Long lockeduserid, Long unlockinguserid);

	List<UserProfile> getAllLockedUser(String userName);

	LogInSettingsLog getById(long id);

	Long getlimitLoginAttempt(Long facilityId);

	List<LogInSettingsLog> findTop3BylockUserIdAndOldPasswrdNotNull(Long lockUserId);

	GetLogInSettingsLogResponse getAllLogInSettingsLog(LogUserFilter filter, Pagination pagination);

	GetUnLockLogResponse getAllUnLockedUserLog(UserLogFilter filter, Pagination pagination);

}
