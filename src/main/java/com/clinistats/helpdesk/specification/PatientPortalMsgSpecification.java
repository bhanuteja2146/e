package com.clinistats.helpdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.helpdesk.config.filter.PatientPortalFilter;
import com.clinistats.helpdesk.model.TicketModel;

public class PatientPortalMsgSpecification implements Specification<TicketModel> {
	private static final long serialVersionUID = -3898038254457581973L;
	private final PatientPortalFilter criteria;
	private List<Predicate> filters;

	public PatientPortalMsgSpecification(PatientPortalFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<TicketModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		filters = new ArrayList<>();
		if (criteria != null) {
			if (criteria.getRecordState() != null) {
				filters.add(criteriaBuilder.equal(root.get("recordState"), criteria.getRecordState()));
			}

			if (criteria.getMailBoxType() != null) {
				filters.add(criteriaBuilder.equal(root.get("mailBoxType"), criteria.getMailBoxType()));
			}

			if (criteria.getPatientId() != null) {
				filters.add(criteriaBuilder.equal(root.get("patientId"), criteria.getPatientId()));
			}
			/*
			 * if (criteria.getQuery() != null) {
			 * filters.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")),
			 * "%" + criteria.getQuery().toLowerCase() + "%")); }
			 */
			
			 if (criteria.getProviderIds() != null && !criteria.getProviderIds().isEmpty()) {
				 filters.add(root.get("providerId").in(criteria.getProviderIds()));
			 }
		}
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
