package com.clinistats.helpdesk.services.interfaces;

import com.clinistats.helpdesk.domain.UserProfile;

public interface UpdateUserProfileUseCase {

	UserProfile updateUserProfile(UserProfile userProfile);

	void limitLoginAttemptSave(Long count, String username);

	void lockStatusSave(String username, String reason);

	UserProfile getByActiveId(String id);

	String unLockUserProfile(String unLockUserName, String logInUserName);

}
