package com.clinistats.helpdesk.repositry;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinistats.helpdesk.model.ContactDetailsModel;

@Repository
@Transactional
public interface ContactDetailsRepository extends JpaRepository<ContactDetailsModel, Long> {

	Long countByCellPhoneIgnoreCase(String cellPhone);

}
