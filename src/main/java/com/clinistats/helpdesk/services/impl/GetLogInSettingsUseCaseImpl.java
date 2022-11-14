package com.clinistats.helpdesk.services.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.config.filter.Filter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UserLogFilter;
import com.clinistats.helpdesk.dao.interfaces.LogInSettingsDao;
import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.response.GetLogInSettingsResponse;
import com.clinistats.helpdesk.response.GetUnLockLogResponse;
import com.clinistats.helpdesk.services.interfaces.GetLogInSettingsUseCase;

@Service
public class GetLogInSettingsUseCaseImpl implements GetLogInSettingsUseCase {

	
	@Autowired
	private LogInSettingsDao logInSettingsDao;
	

	@Override
	public GetLogInSettingsResponse getAllLogInSettings(Filter filter, Pagination pagination) {
		return logInSettingsDao.getAllLogInSettings(filter, pagination);
	}


	@Override
	public LogInSettings getLogInSettingsList(Long facilityId) {
		//List<LogInSettings> listLogInSettingsFinal = new ArrayList<LogInSettings>();
		LogInSettings listLogInSettings = logInSettingsDao.getLogInSettingsList(facilityId);
		

		return listLogInSettings;
	}




	@Override
	public List<UserProfile> getAllLockedUser(String userName) {
		return logInSettingsDao.getAllLockedUser(userName);
	}


	@Override
	public Long getlimitLoginAttempt(Long facilityId) {
		return logInSettingsDao.getlimitLoginAttempt(facilityId);
	}

	@Override
	public GetUnLockLogResponse getAllUnLockedUserLog(UserLogFilter filter, Pagination pagination) {
		return logInSettingsDao.getAllUnLockedUserLog(filter, pagination);
	}

}
