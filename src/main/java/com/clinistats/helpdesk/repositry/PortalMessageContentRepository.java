package com.clinistats.helpdesk.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.TicketResponseContentModel;

@Repository
public interface PortalMessageContentRepository extends JpaRepository<TicketResponseContentModel, Long> {

}
