package com.clinistats.hepdesk.repositry;

/**
 * 
 */

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clinistats.hepdesk.domain.Status;
import com.clinistats.hepdesk.model.UserProfileModel;

/**
 * @author lukman.d
 *
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileModel, Long> {
	UserProfileModel findByUserName(String username);

	UserProfileModel findByEmailId(String emailId);

	@Query(value = "select count(*), facility_id ,facility_name from emr_user_profile eup where facility_id is not null group by facility_id ,facility_name", nativeQuery = true)
	List<Object[]> listAllFacilities();

	List<UserProfileModel> findByFacilityId(Long facilityId);

	Page<UserProfileModel> findAll(Specification<UserProfileModel> spec, Pageable pageable);

	@Modifying
	@Query("update UserProfileModel sp set sp.lastAccessApplication = CURRENT_DATE, sp.lockDate = null,  sp.lockReason = null, sp.lockStatus = :lockStatus  where sp.id = :id ")
	int unLockByLockedUserIdLockStatus(String lockStatus, Long id);

	List<UserProfileModel> findByuserNameContaining(String userName);

	@Modifying
	@Query("update UserProfileModel sp set sp.lockStatus = :lockStatus,sp.lockDate = :lockDate,sp.lockReason = :lockReason  where sp.userName = :userName ")
	int lockStatusSave(String lockStatus, String userName, String lockDate, String lockReason);

	@Modifying
	@Query("update UserProfileModel sp set sp.limitLoginAttempt = :limitLoginAttempt  where sp.userName = :userName ")
	int limitLoginAttemptSave(Long limitLoginAttempt, String userName);

	@Modifying
	@Query("update UserProfileModel sp set sp.lastAccessApplication = CURRENT_DATE , sp.limitLoginAttempt = :limitLoginAttempt  where sp.userName = :userName ")
	int limitLoginAttemptAndLastAccessApplicationSave(Long limitLoginAttempt, String userName);

	UserProfileModel findByMobile(String mobile);

	@Query("select p from UserProfileModel p where p.providerId = :providerId and p.userName = :userName")
	UserProfileModel findByProviderId(Long providerId, String userName);

	@Query("select p from UserProfileModel p where p.status = :active")
	List<UserProfileModel> findAllActiveUser(Status active);

}
