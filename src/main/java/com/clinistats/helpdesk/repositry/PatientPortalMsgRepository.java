package com.clinistats.helpdesk.repositry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.TicketModel;

@Repository
public interface PatientPortalMsgRepository extends JpaRepository<TicketModel, Long> {
 
	Page<TicketModel> findAll(Specification<TicketModel> spec, Pageable pageable);
}
