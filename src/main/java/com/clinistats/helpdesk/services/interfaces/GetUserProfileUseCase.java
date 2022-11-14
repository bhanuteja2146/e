package com.clinistats.helpdesk.services.interfaces;


import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UserFilter;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.response.UserProfilePageable;

public interface GetUserProfileUseCase {
	
	UserProfile getById(Long id);

	UserProfilePageable getUserProfileList(UserFilter filter, Pagination pagination);

	Long getlimitLoginAttemptCount(String username);
	UserProfile getByName(String username);
	Long findFacilityId(String username);

//	UserProfilePageable getLockUserProfileList(UserFilter filter, Pagination pagination);

//	UserProfilePageable getUnlockUserProfileList(UnlockUserFilter filter, Pagination pagination);

}
