package com.clinistats.helpdesk.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.clinistats.helpdesk.config.filter.UnlockUserFilter;
import com.clinistats.helpdesk.model.UserProfileModel;

public class UnlockedUserSpecification implements Specification<UserProfileModel>{
	private static final long serialVersionUID = 1L;
	private final UnlockUserFilter criteria;
	private List<Predicate> filters;
	
	private static final String UNLOCK_STATUS = "Unlock";
	
	public UnlockedUserSpecification(UnlockUserFilter criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<UserProfileModel> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		
		filters = new ArrayList<>();
		if (criteria != null) 
		{	
			if(criteria.getQuery()!=null  && !"".equals(criteria.getQuery())) {
				Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + criteria.getQuery().toLowerCase() + "%");
				Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + criteria.getQuery().toLowerCase() + "%");
				filters.add(criteriaBuilder.or(firstName, lastName));
			}			
			
			if(criteria.getFacilityName()!=null  && !"".equals(criteria.getFacilityName()))
				filters.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityName")), "%" + criteria.getFacilityName().toLowerCase() + "%"));
			
			if(criteria.getStatus() !=null) 
				filters.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
			
			if(criteria.getUserType() !=null) 
				filters.add(criteriaBuilder.equal(root.get("userType"), criteria.getUserType()));
			
		}
		
		
		Predicate lockStatusNulllCheck = criteriaBuilder.isNull(root.get("lockStatus"));
		Predicate lockStatus =  criteriaBuilder.equal(root.get("lockStatus"), UNLOCK_STATUS);
		filters.add(criteriaBuilder.or(lockStatusNulllCheck, lockStatus));
		
		
		query.distinct(true);
		return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}