package com.clinistats.helpdesk.dao.interfaces;

import java.util.List;

import com.clinistats.helpdesk.config.filter.Pagination;
import com.clinistats.helpdesk.config.filter.UnlockUserFilter;
import com.clinistats.helpdesk.config.filter.UserFilter;
import com.clinistats.helpdesk.domain.UserProfile;
import com.clinistats.helpdesk.request.DeleteUserRequest;
import com.clinistats.helpdesk.response.UserProfilePageable;


/**
 * @author amar
 *
 */

public interface UserProfileDao {

	
	UserProfile createUserprofile(UserProfile userProfile) throws Exception;
	UserProfile updateUserprofile(UserProfile userProfile);
	
	List<String> disableUserprofiles(List<Long> userProfiles);
	UserProfile getById(Long id);
	UserProfilePageable getUserProfile(UserFilter filter, Pagination pagination);
	//void lockStatusSave(String username);
	void limitLoginAttemptSave(Long count,String username);
	Long getlimitLoginAttemptCount(String username);
	UserProfile getByUserName(String userName);
	Long findFacilityId(String username);
	void lockStatusSave(String userName, String reason);
	UserProfilePageable getLockUserProfile(UserFilter filter, Pagination pagination);
	UserProfilePageable getUnlockUserProfile(UnlockUserFilter filter, Pagination pagination);
	
//	String storeUserData(MobileOTP dto);
	UserProfile getUserByMobile(String mobile);
	UserProfile getUserByEmailId(String emailId);
//	MobileOTP getNewUserByMobile(String mobile);
//	boolean checkIfNewUserExist(String mobileNo);
//	PatientInsuranceProfile addPatientInsurance(PatientInsuranceProfile profile,MultipartFile file);
//	UserProfile addUserProfileInPortal(UserProfile userProfile) throws Exception;
//	PatientInsuranceProfile updatePatientInsurance(PatientInsuranceProfile profile,MultipartFile file) throws Exception;
//	String deletePatientInsurance(Long id);
//	GetAllPatientInsuranceResponse getAllPatientInsurance(PatientInsuranceFilter filter, Pagination pagination);
//	PatientInsuranceProfile getPatientInsurance (Long id);
	
	UserProfile getByActiveId(Long id);
	List<String> changeUserStatus(List<DeleteUserRequest> request);
	String unLockUserProfile(String unLockUserName, String logInUserName);
}
