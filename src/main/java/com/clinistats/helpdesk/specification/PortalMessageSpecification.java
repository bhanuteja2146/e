package com.clinistats.helpdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.helpdesk.config.filter.PortalMessageFilter;
import com.clinistats.helpdesk.model.TicketResponseModel;

public class PortalMessageSpecification  implements Specification<TicketResponseModel>
{
	 
	private static final long serialVersionUID = -6833845676307962448L;
	private final PortalMessageFilter criteria;
	private List<Predicate> filters;
	
	public PortalMessageSpecification(PortalMessageFilter criteria) {
		super();
		this.criteria = criteria;
	}
	

	@Override
	public Predicate toPredicate(Root<TicketResponseModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
	{
		filters = new ArrayList<>(); 
		if (criteria != null) {
			if (criteria.getRecordState() != null) {
				filters.add(criteriaBuilder.equal(root.get("recordState"), criteria.getRecordState()));
			}			
		}
		
		if (criteria.getMailBoxType() != null) {
			filters.add(criteriaBuilder.equal(root.get("mailBoxType"), criteria.getMailBoxType()));
		}	
		
		if (criteria.getProviderId() != null && !"".equals(criteria.getProviderId() )) {
			filters.add(criteriaBuilder.equal(root.get("providerId"), criteria.getProviderId()));
		}
		if (criteria.getPatientId() != null && !"".equals(criteria.getPatientId() )) {
			filters.add(criteriaBuilder.equal(root.get("patientId"), criteria.getPatientId()));
		}	
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));	
	}
}


