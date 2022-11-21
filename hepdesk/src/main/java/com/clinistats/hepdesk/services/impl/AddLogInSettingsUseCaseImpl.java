/**
 * 
 */
package com.clinistats.hepdesk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.hepdesk.dao.interfaces.LogInSettingsDao;
import com.clinistats.hepdesk.domain.LogInSettings;
import com.clinistats.hepdesk.services.interfaces.AddLogInSettingsUseCase;

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
