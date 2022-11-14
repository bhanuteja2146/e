/**
 * 
 */
package com.clinistats.helpdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.config.filter.LogUserFilter;
import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.dao.interfaces.LogInSettingsDao;
import com.clinistats.helpdesk.domain.LogInSettingsLog;
import com.clinistats.helpdesk.response.GetLogInSettingsLogResponse;
import com.clinistats.helpdesk.services.interfaces.AddLogInSettingsLogUseCase;

/**
 * @author giri
 *
 */

@Service
public class AddLogInSettingsLogUseCaseImpl implements AddLogInSettingsLogUseCase{

	@Autowired
	private LogInSettingsDao logInSettingsDao;

	


	@Override
	public LogInSettingsLog addLogInSettingsLog(LogInSettingsLog domain) {
		return logInSettingsDao.addLogInSettingsLog(domain);
	}
	
	@Override
	public GetLogInSettingsLogResponse getAllLogInSettingsLog(LogUserFilter filter, Pagination pagination) {
		return logInSettingsDao.getAllLogInSettingsLog(filter, pagination);
	}

}
