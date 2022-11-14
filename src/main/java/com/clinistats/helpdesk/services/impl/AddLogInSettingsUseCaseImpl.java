/**
 * 
 */
package com.clinistats.helpdesk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.dao.interfaces.LogInSettingsDao;
import com.clinistats.helpdesk.domain.LogInSettings;
import com.clinistats.helpdesk.services.interfaces.AddLogInSettingsUseCase;

/**
 * @author giri
 *
 */

@Service
public class AddLogInSettingsUseCaseImpl implements AddLogInSettingsUseCase {

	@Autowired
	private LogInSettingsDao logInSettingsDao;

	@Override
	public List<LogInSettings> addLogInSettings(List<LogInSettings> domain) {
		return logInSettingsDao.addLogInSettings(domain);
	}

}
