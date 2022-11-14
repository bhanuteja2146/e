package com.clinistats.helpdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.dao.interfaces.UserProfileDao;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.services.interfaces.UpdateUserProfileUseCase;

@Service
public class UpdateUserProfileUseCaseImpl implements UpdateUserProfileUseCase {

	@Autowired
	private UserProfileDao userProfileDao;

	@Override
	public UserProfile updateUserProfile(UserProfile userProfile) {

		return userProfileDao.updateUserprofile(userProfile);
	}

	@Override
	public void lockStatusSave(String username, String reason) {
		userProfileDao.lockStatusSave(username, reason);

	}

	@Override
	public void limitLoginAttemptSave(Long count, String username) {
		userProfileDao.limitLoginAttemptSave(count, username);

	}

	@Override
	public UserProfile getByActiveId(String id) {
		return userProfileDao.getByActiveId(Long.valueOf(id));

	}

	@Override
	public String unLockUserProfile(String unLockUserName, String logInUserName) {
		return userProfileDao.unLockUserProfile(unLockUserName, logInUserName);

	}

}
