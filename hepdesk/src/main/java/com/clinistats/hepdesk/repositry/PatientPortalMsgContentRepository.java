package com.clinistats.hepdesk.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.hepdesk.model.TicketContentModel;

@Repository
public interface PatientPortalMsgContentRepository extends JpaRepository<TicketContentModel, Long> {

}
