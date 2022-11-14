package com.clinistats.hepdesk.services.interfaces;


import com.clinistats.hepdesk.config.filter.Pagination;
import com.clinistats.hepdesk.config.filter.UserFilter;
import com.clinistats.hepdesk.domain.UserProfile;
import com.clinistats.hepdesk.response.UserProfilePageable;

public interface GetUserProfileUseCase {
	
	UserProfile getById(Long id);

	UserProfilePageable getUserProfileList(UserFilter filter, Pagination pagination);

	Long getlimitLoginAttemptCount(String username);
	UserProfile getByName(String username);
	Long findFacilityId(String username);

//	UserProfilePageable getLockUserProfileList(UserFilter filter, Pagination pagination);

//	UserProfilePageable getUnlockUserProfileList(UnlockUserFilter filter, Pagination pagination);

}
