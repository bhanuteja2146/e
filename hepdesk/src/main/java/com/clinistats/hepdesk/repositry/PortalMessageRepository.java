package com.clinistats.hepdesk.repositry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.hepdesk.model.TicketResponseModel;
@Repository
public interface PortalMessageRepository extends JpaRepository<TicketResponseModel, Long>
{ 
	Page<TicketResponseModel> findAll(Specification<TicketResponseModel> spec, Pageable pageable);
}
