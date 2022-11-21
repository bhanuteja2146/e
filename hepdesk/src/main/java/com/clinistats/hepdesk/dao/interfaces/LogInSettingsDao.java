package com.clinistats.hepdesk.dao.interfaces;

/**
 * 
 */

import java.util.List;

import com.clinistats.hepdesk.config.filter.Filter;
import com.clinistats.hepdesk.config.filter.LogUserFilter;
import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.UserLogFilter;
import com.clinistats.hepdesk.domain.CustomObjectUpdate;
import com.clinistats.hepdesk.domain.LogInSettings;
import com.clinistats.hepdesk.domain.LogInSettingsLog;
import com.clinistats.hepdesk.domain.UserProfile;
import com.clinistats.hepdesk.response.GetLogInSettingsLogResponse;
import com.clinistats.hepdesk.response.GetLogInSettingsResponse;
import com.clinistats.hepdesk.response.GetUnLockLogResponse;

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
