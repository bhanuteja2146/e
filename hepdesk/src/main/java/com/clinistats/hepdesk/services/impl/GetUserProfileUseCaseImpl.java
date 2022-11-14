package com.clinistats.hepdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.UserFilter;
import com.clinistats.hepdesk.dao.interfaces.UserProfileDao;
import com.clinistats.hepdesk.domain.UserProfile;
import com.clinistats.hepdesk.response.UserProfilePageable;
import com.clinistats.hepdesk.services.interfaces.GetUserProfileUseCase;

@Service
public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {

	@Autowired
	private UserProfileDao userProfileDao;

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

//	@Override
//	public UserProfilePageable getLockUserProfileList(UserFilter filter, Pagination pagination) {
//		return userProfileDao.getLockUserProfile(filter, pagination);
//	}
//	
//	
//	@Override
//	public UserProfilePageable getUnlockUserProfileList(UnlockUserFilter filter, Pagination pagination) {
//		return userProfileDao.getUnlockUserProfile(filter, pagination);
//	}

	@Override
	public Long getlimitLoginAttemptCount(String username) {
		return userProfileDao.getlimitLoginAttemptCount(username);
	}

	@Override
	public Long findFacilityId(String username) {
		return userProfileDao.findFacilityId(username);
	}

}
