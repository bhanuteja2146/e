package com.clinistats.helpdesk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UnlockUserFilter;
import com.clinistats.helpdesk.config.filter.UserFilter;
import com.clinistats.helpdesk.dao.interfaces.UserProfileDao;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.response.UserProfilePageable;
import com.clinistats.helpdesk.services.interfaces.UserProfileUseCase;

@Service
public class UserProfileUseCaseImpl implements UserProfileUseCase {

	@Autowired
	private UserProfileDao userProfileDao;

	@Override
	public UserProfile addUserProfile(UserProfile userProfile) throws Exception {
		return userProfileDao.createUserprofile(userProfile);
	}

	@Override
	public UserProfile getById(Long id) {
		return userProfileDao.getById(id);
	}
	
	@Override
	public UserProfile getByName(String userName) {
		return userProfileDao.getByUserName(userName);
	}
	
	
	
	@Override
	public UserProfilePageable getUserProfileList(UserFilter filter, Pagination pagination) {
		return userProfileDao.getUserProfile(filter, pagination);
	}
	
	@Override
	public UserProfilePageable getLockUserProfileList(UserFilter filter, Pagination pagination) {
		return userProfileDao.getLockUserProfile(filter, pagination);
	}
	
	
	@Override
	public UserProfilePageable getUnlockUserProfileList(UnlockUserFilter filter, Pagination pagination) {
		return userProfileDao.getUnlockUserProfile(filter, pagination);
	}


	@Override
	public Long getlimitLoginAttemptCount(String username) {
		return userProfileDao.getlimitLoginAttemptCount(username);
	}

	@Override
	public Long findFacilityId(String username) {
		return userProfileDao.findFacilityId(username);
	}

	@Override
	public List<String> changeUserProfileStatus(List<Long> userProfiles) {
		return userProfileDao.disableUserprofiles(userProfiles);
	}
}
