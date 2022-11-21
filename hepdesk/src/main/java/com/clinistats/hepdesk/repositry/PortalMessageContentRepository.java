package com.clinistats.hepdesk.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.hepdesk.model.TicketResponseContentModel;

@Repository
public interface PortalMessageContentRepository extends JpaRepository<TicketResponseContentModel, Long> {

}
