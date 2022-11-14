package com.clinistats.helpdesk.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.TicketContentModel;

@Repository
public interface PatientPortalMsgContentRepository extends JpaRepository<TicketContentModel, Long> {

}
