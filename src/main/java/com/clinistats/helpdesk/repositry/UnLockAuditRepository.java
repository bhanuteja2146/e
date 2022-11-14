package com.clinistats.helpdesk.repositry;

/**
 * 
 */

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.UnLockAuditModel;
import com.clinistats.helpdesk.model.UserProfileModel;

/**
 * @author Bhanu Teja
 *
 */
@Repository
public interface UnLockAuditRepository extends JpaRepository<UnLockAuditModel, Long> {

	List<UserProfileModel> findByuserNameContaining(String userName);

	Page<UnLockAuditModel> findAll(Specification<UnLockAuditModel> spec, Pageable pageable);
	
	
}
