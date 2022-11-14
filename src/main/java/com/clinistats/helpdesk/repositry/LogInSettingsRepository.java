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

import com.clinistats.helpdesk.model.LogInSettingsModel;

/**
 * @author giri
 *
 */
@Repository
public interface LogInSettingsRepository extends JpaRepository<LogInSettingsModel, Long> {

	Page<LogInSettingsModel> findAll(Specification<LogInSettingsModel> spec, Pageable pageable);

	List<LogInSettingsModel> findAll();

	LogInSettingsModel findByFacilityId(Long facilityId);

}
