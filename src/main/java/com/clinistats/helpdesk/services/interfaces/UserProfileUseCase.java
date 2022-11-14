/**
 * 
 */
package com.clinistats.helpdesk.services.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UnlockUserFilter;
import com.clinistats.helpdesk.config.filter.UserFilter;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.response.UserProfilePageable;

/**
 * @author LENOVO
 *
 */
public interface UserProfileUseCase {

	UserProfile addUserProfile(UserProfile userProfile) throws Exception;

	UserProfile getById(Long id);

	UserProfilePageable getUserProfileList(UserFilter filter, Pagination pagination);

	Long getlimitLoginAttemptCount(String username);

	UserProfile getByName(String username);

	Long findFacilityId(String username);

	UserProfilePageable getLockUserProfileList(UserFilter filter, Pagination pagination);

	UserProfilePageable getUnlockUserProfileList(UnlockUserFilter filter, Pagination pagination);

	List<String> changeUserProfileStatus(List<Long> userProfiles);
}
