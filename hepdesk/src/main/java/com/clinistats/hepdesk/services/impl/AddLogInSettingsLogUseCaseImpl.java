/**
 * 
 */
package com.clinistats.hepdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.hepdesk.config.filter.LogUserFilter;
import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.dao.interfaces.LogInSettingsDao;
import com.clinistats.hepdesk.domain.LogInSettingsLog;
import com.clinistats.hepdesk.response.GetLogInSettingsLogResponse;
import com.clinistats.hepdesk.services.interfaces.AddLogInSettingsLogUseCase;

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
