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
import com.clinistats.hepdesk.model.LogInSettingsLogModel;

/**
 * @author giri
 *
 */
@Repository
public interface LogInSettingsLogRepository extends JpaRepository<LogInSettingsLogModel, Long> {
	
	@Modifying
	@Query("update LogInSettingsLogModel sp set sp.lockUserId = :lockUserId,sp.unLockingUserId= :unLockingUserId,sp.unLockDate= :unLockDate  where sp.recordState = :recordState and sp.lockUserId= :lockUserId and sp.unLockingUserId = :unLockingUserId ")
	int unLockByLockedUserId(Status recordState, Long lockUserId, Long unLockingUserId, String unLockDate);
    
	@Modifying
	@Query("update LogInSettingsLogModel sp set sp.oldPasswrd = :oldPasswrd, sp.userId= :userId ")
	void updateOldPasswrd(long userId, String oldPasswrd);
	
	List<LogInSettingsLogModel> findTop3BylockUserIdAndOldPasswrdIsNotNullOrderByIdDesc(Long lockUserId);
	
	Page<LogInSettingsLogModel> findAll(Specification<LogInSettingsLogModel> spec, Pageable pageable);


}
